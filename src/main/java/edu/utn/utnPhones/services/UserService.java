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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    //todo Revisar bien el add de nuevo por las dudas, pero creo que ya esta andando
    public User add(UserDtoAdd user){

        City city = cityRepository.findByNameAndAreaCodeAndProvince(user.getCity(), user.getAreaCode(), user.getProvince())
                .orElseThrow(() -> new NotFoundException(Constants.CITY_NOT_EXIST));

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
                .orElseThrow(() -> new NotFoundException(Constants.USER_NOT_EXIST));

        deletedUser.setRemoved(true);
        userRepository.save(deletedUser);
    }

    /*public void update(Integer idUser, UserDtoPut updatedUser) {

        User oldUser = userRepository.findByIdAndRemoved(idUser, false)
                .orElseThrow(() -> new NotFoundException(Constants.USER_NOT_EXIST));

        if (userRepository.findByIdAndDniAndUserType(updatedUser.getDni(), oldUser.getUserType(), oldUser.getId()) != null){
            throw new UserAlreadyExistsException(Constants.NOT_UPDATED_USER);
        }

        //todo Tener en cuenta que no se compare con el mismo
        if (userRepository.findByUserNameAndRemoved(updatedUser.getUserName(), false) != null) {
            throw new DuplicatedUsernameException(Constants.DUPLICATED_USERNAME);
        }

        City city = cityRepository.findByNameAndAreaCodeAndProvince(updatedUser.getCity().getName(), updatedUser.getCity().getAreaCode(), updatedUser.getCity().getProvince().getName())
                .orElseThrow(() -> new NotFoundException(Constants.CITY_NOT_EXIST));

        userRepository.save(User.builder().id(idUser).city(city)
                .dni(updatedUser.getDni()).name(updatedUser.getName()).lastName(updatedUser.getLastName())
                .pwd(updatedUser.getPwd()).userName(updatedUser.getUserName()).build());
    }

    public User partialUpdate(Integer idUser, UserDtoPatch user) {

        User oldUser = userRepository.findByIdAndRemoved(idUser, false)
                .orElseThrow(() -> new NotFoundException(Constants.USER_NOT_EXIST));

        if (user.getDni() != null) {
            if (userRepository.findByIdAndDniAndUserType(user.getDni(), oldUser.getUserType(), oldUser.getId()) != null) {
                throw new UserAlreadyExistsException(Constants.NOT_UPDATED_USER);
            }
        }

        if (user.getUserName() != null) {
            if (userRepository.findByUserNameAndRemoved(user.getUserName(), false) != null) {
                throw new DuplicatedUsernameException(Constants.DUPLICATED_USERNAME);
            }
        }

        City city = null;
        if (user.getCity() != null) {
            city = cityRepository.findByNameAndAreaCodeAndProvince(user.getCity().getName(), user.getCity().getAreaCode(), user.getCity().getProvince().getName())
                    .orElseThrow(() -> new NotFoundException(Constants.CITY_NOT_EXIST));
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
