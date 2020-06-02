package edu.utn.utnPhones.services;

import edu.utn.utnPhones.exceptions.DuplicatedUsernameException;
import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.exceptions.UserAlreadyExistsException;
import edu.utn.utnPhones.models.City;
import edu.utn.utnPhones.models.dtos.UserDtoAdd;
import edu.utn.utnPhones.models.dtos.UserDtoPatch;
import edu.utn.utnPhones.repositories.CityRepository;
import edu.utn.utnPhones.repositories.UserRepository;
import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.UserType;
import edu.utn.utnPhones.models.dtos.UserDtoPut;
import edu.utn.utnPhones.models.projections.ClientsWithoutPassword;
import edu.utn.utnPhones.models.projections.UsersWithoutPassword;
import edu.utn.utnPhones.utils.Constants;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class UserService {

    private UserRepository userRepository;
    private CityRepository cityRepository;

    @Autowired
    public UserService(UserRepository userRepository, CityRepository cityRepository) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
    }

    public List<UsersWithoutPassword> getAll(){
        return userRepository.getAll();
    }

    public User getClient(String userName) {
        return userRepository.findByUserNameAndRemovedAndUserType(userName, false, UserType.client)
                .orElseThrow(() -> new NotFoundException(Constants.USER_NOT_EXIST_USERNAME));
    }

    public User add(UserDtoAdd user){

        City city = cityRepository.findByNameAndAreaCodeAndProvince(user.getCity(), user.getAreaCode(), user.getProvince())
                .orElseThrow(() -> new NotFoundException(Constants.LOCATION_NOT_EXIST));

        User newUser = User.builder().name(user.getName()).lastName(user.getLastName()).dni(user.getDni())
                        .userName(user.getUserName()).pwd(user.getPwd()).userType(user.getUserType())
                        .city(city).removed(false).phoneLines(null).build();

        if (userRepository.findByUserNameAndRemoved(newUser.getUserName(), false) != null) {
            throw new DuplicatedUsernameException(Constants.DUPLICATED_USERNAME);
        }

        User previousUser = userRepository.findByDniAndUserType(user.getDni(), user.getUserType());

        if (previousUser != null) {
            if (previousUser.getRemoved()) {
                newUser.setId(previousUser.getId());
            } else {
                throw new UserAlreadyExistsException(Constants.NOT_ADDED_USER);
            }
        }

        newUser = userRepository.save(newUser);
        newUser.setId(userRepository.getIdByUserName(newUser.getUserName(), false));
        return newUser;
    }

    public List<ClientsWithoutPassword> getClients() {
        return userRepository.findByUserTypeAndRemoved(UserType.client, false);
    }

    //todo Fijarse si cuando se elimina un usuario hay que eliminar sus phone lines
    public void remove(Integer idUser) {

        User deletedUser = userRepository.findByIdAndRemoved(idUser, false)
                .orElseThrow(() -> new NotFoundException(Constants.USER_NOT_EXIST_ID));

        deletedUser.setRemoved(true);
        userRepository.save(deletedUser);
    }

    public User update(Integer idUser, UserDtoPut updatedUser) {

        User oldUser = userRepository.findByIdAndRemoved(idUser, false)
                .orElseThrow(() -> new NotFoundException(Constants.USER_NOT_EXIST_ID));
        Integer testUser = userRepository.existsByIdAndDniAndUserTypeAndRemoved(idUser, updatedUser.getDni(), oldUser.getUserType(), false);

        if (userRepository.existsByIdAndDniAndUserTypeAndRemoved(idUser, updatedUser.getDni(), oldUser.getUserType(), false) > 0){
            throw new UserAlreadyExistsException(Constants.NOT_UPDATED_USER); //todo cambiar en patch
        }

        if (userRepository.findByIdAndUserNameAndRemoved(updatedUser.getUserName(), false, idUser) != null) {
            throw new DuplicatedUsernameException(Constants.DUPLICATED_USERNAME);
        }

        City city = cityRepository.findByNameAndAreaCodeAndProvince(updatedUser.getCity(), updatedUser.getAreaCode(), updatedUser.getProvince())
                .orElseThrow(() -> new NotFoundException(Constants.LOCATION_NOT_EXIST));

        return userRepository.save(User.builder().id(idUser).city(city) //todo cambiar en patch
                .dni(updatedUser.getDni()).name(updatedUser.getName()).lastName(updatedUser.getLastName())
                .pwd(updatedUser.getPwd()).userName(updatedUser.getUserName()).removed(oldUser.getRemoved())
                .userType(oldUser.getUserType()).phoneLines(oldUser.getPhoneLines()).build());
    }

    /*public User partialUpdate(Integer idUser, UserDtoPatch user) {

        User oldUser = userRepository.findByIdAndRemoved(idUser, false)
                .orElseThrow(() -> new NotFoundException(Constants.USER_NOT_EXIST_ID));

        if (user.getDni() != null) {
            if (userRepository.findByIdAndDniAndUserType(user.getDni(), oldUser.getUserType(), oldUser.getId()) != null) {
                throw new UserAlreadyExistsException(Constants.NOT_UPDATED_USER);
            }
        }

        if (user.getUserName() != null) {
            if (userRepository.findByIdAndUserNameAndRemoved(user.getUserName(), false, idUser) != null) {
                throw new DuplicatedUsernameException(Constants.DUPLICATED_USERNAME);
            }
        }

        City city = null;
        if (user.getCity() != null) {
            city = cityRepository.findByNameAndAreaCodeAndProvince(user.getCity(), user.getAreaCode(), user.getProvince())
                    .orElseThrow(() -> new NotFoundException(Constants.LOCATION_NOT_EXIST));
        }

        Optional.ofNullable(user.getName()).ifPresent(oldUser::setName);
        Optional.ofNullable(user.getLastName()).ifPresent(oldUser::setLastName);
        Optional.ofNullable(user.getDni()).ifPresent(oldUser::setDni);
        Optional.ofNullable(user.getUserName()).ifPresent(oldUser::setUserName);
        Optional.ofNullable(user.getPwd()).ifPresent(oldUser::setPwd);
        Optional.ofNullable(city).ifPresent(oldUser::setCity);

        return userRepository.save(oldUser);
    }*/

}
