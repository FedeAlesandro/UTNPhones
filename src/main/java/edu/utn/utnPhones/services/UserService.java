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

    public User getClient(String userName) {
        return userRepository.findByUserNameAndRemovedAndUserType(userName, false, UserType.client)
                .orElseThrow(() -> new NotFoundException(Constants.USER_NOT_EXIST_USERNAME));
    }

    public List<ClientsWithoutPassword> getClients() {
        return userRepository.findByUserTypeAndRemoved(UserType.client, false);
    }

    public User add(UserDtoAdd user){

        City city = cityVerification(user.getCity(), user.getAreaCode(), user.getProvince());

        User newUser = User.builder().name(user.getName()).lastName(user.getLastName()).dni(user.getDni())
                        .userName(user.getUserName()).pwd(user.getPwd()).userType(user.getUserType())
                        .city(city).removed(false).phoneLines(null).build();

        userNameVerification(newUser.getUserName(),null);

        Integer idPrevious = dniSearch(user.getDni());
        if (idPrevious != null) {
            newUser.setId(idPrevious);
        }

        newUser = userRepository.save(newUser);
        newUser.setId(userRepository.getIdByUserName(newUser.getUserName(), false));
        return newUser;
    }

    public void remove(Integer idUser) {

        User deletedUser = idRemovedVerification(idUser);

        deletedUser.setRemoved(true);
        userRepository.save(deletedUser);
    }

    public User update(Integer idUser, UserDtoPut updatedUser) {

        User oldUser = idRemovedVerification(idUser);

        dniVerification(updatedUser.getDni(), idUser);

        userNameVerification(updatedUser.getUserName(), idUser);

        City city = cityVerification(updatedUser.getCity(), updatedUser.getAreaCode(), updatedUser.getProvince());

        return userRepository.save(User.builder().id(idUser).city(city)
                .dni(updatedUser.getDni()).name(updatedUser.getName()).lastName(updatedUser.getLastName())
                .pwd(updatedUser.getPwd()).userName(updatedUser.getUserName()).removed(oldUser.getRemoved())
                .userType(oldUser.getUserType()).phoneLines(oldUser.getPhoneLines()).build());
    }

    public User partialUpdate(Integer idUser, UserDtoPatch updatedUser) {

        User oldUser = idRemovedVerification(idUser);

        if (updatedUser.getDni() != null){
            dniVerification(updatedUser.getDni(), idUser);
        }

        if (updatedUser.getUserName() != null) {
            userNameVerification(updatedUser.getUserName(), idUser);
        }

        City city = null;
        if (updatedUser.getCity() != null) {
            city = cityVerification(updatedUser.getCity(), updatedUser.getAreaCode(), updatedUser.getProvince());
        }

        Optional.ofNullable(updatedUser.getName()).ifPresent(oldUser::setName);
        Optional.ofNullable(updatedUser.getLastName()).ifPresent(oldUser::setLastName);
        Optional.ofNullable(updatedUser.getDni()).ifPresent(oldUser::setDni);
        Optional.ofNullable(updatedUser.getUserName()).ifPresent(oldUser::setUserName);
        Optional.ofNullable(updatedUser.getPwd()).ifPresent(oldUser::setPwd);
        Optional.ofNullable(city).ifPresent(oldUser::setCity);

        return userRepository.save(oldUser);
    }


    private City cityVerification(String city, String areaCode, String province){
        return cityRepository.findByNameAndAreaCodeAndProvince(city, areaCode, province)
                .orElseThrow(() -> new NotFoundException(Constants.LOCATION_NOT_EXIST));
    }

    private void userNameVerification(String userName, Integer idUser){

        if (idUser == null) {
            if (userRepository.findByUserNameAndRemoved(userName, false) != null) {
                throw new DuplicatedUsernameException(Constants.DUPLICATED_USERNAME);
            }
        } else {
            if (userRepository.findByIdAndUserNameAndRemoved(userName, false, idUser) != null) {
                throw new DuplicatedUsernameException(Constants.DUPLICATED_USERNAME);
            }
        }
    }

    private Integer dniSearch(String dni){

        User previousUser = userRepository.findByDni(dni);

        if (previousUser != null) {
            if (previousUser.getRemoved()) {
                return previousUser.getId();
            } else {
                throw new UserAlreadyExistsException(Constants.NOT_ADDED_USER);
            }
        }
        return null;
    }

    private User idRemovedVerification(Integer idUser){
        return userRepository.findByIdAndRemoved(idUser, false)
                .orElseThrow(() -> new NotFoundException(Constants.USER_NOT_EXIST_ID));
    }

    private void dniVerification(String dni, Integer idUser){

        User previousUser = userRepository.findByDniAndId(dni, idUser);

        if (previousUser != null) {
            throw new UserAlreadyExistsException(Constants.NOT_UPDATED_USER);
        }
    }
}
