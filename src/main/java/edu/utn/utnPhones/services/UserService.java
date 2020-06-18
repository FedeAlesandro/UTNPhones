package edu.utn.utnPhones.services;

import edu.utn.utnPhones.exceptions.DuplicatedUsernameException;
import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.exceptions.UnauthorizedUserTypeException;
import edu.utn.utnPhones.exceptions.UserAlreadyExistsException;
import edu.utn.utnPhones.models.City;
import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.dtos.requests.UserDtoAdd;
import edu.utn.utnPhones.models.dtos.requests.UserDtoPatch;
import edu.utn.utnPhones.models.dtos.requests.UserDtoPut;
import edu.utn.utnPhones.models.enums.UserType;
import edu.utn.utnPhones.models.projections.ClientsWithoutPassword;
import edu.utn.utnPhones.repositories.CityRepository;
import edu.utn.utnPhones.repositories.UserRepository;
import edu.utn.utnPhones.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static edu.utn.utnPhones.utils.Constants.DUPLICATED_USERNAME;
import static edu.utn.utnPhones.utils.Constants.LOCATION_NOT_EXIST;
import static edu.utn.utnPhones.utils.Constants.LOGIN_FAILED;
import static edu.utn.utnPhones.utils.Constants.NOT_ADDED_USER;
import static edu.utn.utnPhones.utils.Constants.NOT_UPDATED_USER;
import static edu.utn.utnPhones.utils.Constants.UNAUTHORIZED_USER_HANDLING;
import static edu.utn.utnPhones.utils.Constants.USER_NOT_EXIST;
import static edu.utn.utnPhones.utils.Constants.USER_NOT_EXIST_ID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PhoneLineService phoneLineService;

    private final CityRepository cityRepository;

    private final PasswordEncoder passwordEncoder;

    public User getClient(String userName) {

        return userRepository.findByUserNameAndRemovedAndUserType(userName, false, UserType.client)
                .orElseThrow(() -> new NotFoundException(Constants.USER_NOT_EXIST_USERNAME));
    }

    public List<ClientsWithoutPassword> getClients() {

        return userRepository.findByUserTypeAndRemoved(UserType.client, false);
    }

    public URI add(UserDtoAdd user){

        userTypeVerification(UserType.getUserType(user.getUserType()));
        City city = cityVerification(user.getCity(), user.getAreaCode(), user.getProvince());

        User newUser = User.fromUserDtoAdd(user, city);
        userNameVerification(newUser.getUserName(),null);
        dniSearch(user.getDni()).ifPresent(newUser::setId);

        newUser.setPwd(passwordEncoder.encode(newUser.getPwd()));
        newUser = userRepository.save(newUser);
        newUser.setId(userRepository.getIdByUserName(newUser.getUserName(), false));

        return getLocation(newUser);
    }

    private URI getLocation(User user) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idUser}")
                .buildAndExpand(user.getId())
                .toUri();
    }

    public void remove(Integer idUser) {

        User deletedUser = idRemovedVerification(idUser);
        userTypeVerification(deletedUser.getUserType());
        deletedUser.setRemoved(true);

        deletedUser.getPhoneLines()
                .forEach(phoneLine -> phoneLineService.remove(phoneLine.getId()));

        userRepository.save(deletedUser);
    }

    public User update(Integer idUser, UserDtoPut updatedUser) {

        User oldUser = idRemovedVerification(idUser);
        userTypeVerification(oldUser.getUserType());
        dniVerification(updatedUser.getDni(), idUser);
        userNameVerification(updatedUser.getUserName(), idUser);
        updatedUser.setPwd(passwordEncoder.encode(updatedUser.getPwd()));

        City city = cityVerification(updatedUser.getCity(), updatedUser.getAreaCode(), updatedUser.getProvince());

        return userRepository.save(User.fromUserDtoPut(updatedUser, oldUser, city));
    }

    public User partialUpdate(Integer idUser, UserDtoPatch updatedUser) {

        User oldUser = idRemovedVerification(idUser);
        userTypeVerification(oldUser.getUserType());

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
        Optional.ofNullable(updatedUser.getPwd()).ifPresent(pwd -> oldUser.setPwd(passwordEncoder.encode(pwd)));
        Optional.ofNullable(city).ifPresent(oldUser::setCity);

        return userRepository.save(oldUser);
    }

    public User login(String username, String pwd){

        User user = userRepository.findByUserNameAndRemoved(username,false)
                .orElseThrow(() -> new NotFoundException(USER_NOT_EXIST));

        if(passwordEncoder.matches(pwd, user.getPwd()))
            return user;
        else
            throw new NotFoundException(LOGIN_FAILED);
    }

    private City cityVerification(String city, String areaCode, String province){

        return cityRepository.findByNameAndAreaCodeAndProvince(city, areaCode, province)
                .orElseThrow(() -> new NotFoundException(LOCATION_NOT_EXIST));
    }

    private void userNameVerification(String userName, Integer idUser){

        if (idUser == null) {
            if (userRepository.findByUserNameAndRemoved(userName, false).isPresent())
                throw new DuplicatedUsernameException(DUPLICATED_USERNAME);
        }else {
            if (userRepository.findByIdAndUserNameAndRemoved(userName, false, idUser).isPresent())
                throw new DuplicatedUsernameException(DUPLICATED_USERNAME);
        }
    }

    private void userTypeVerification(UserType userType){

        if (userType.equals(UserType.infrastructure) || userType.equals(UserType.employee)) {
            throw new UnauthorizedUserTypeException(UNAUTHORIZED_USER_HANDLING);
        }
    }

    private Optional<Integer> dniSearch(String dni){

        User previousUser = userRepository.findByDni(dni);

        if (previousUser != null) {
            if (previousUser.getRemoved()) {

                return Optional.of(previousUser.getId());
            } else {
                throw new UserAlreadyExistsException(NOT_ADDED_USER);
            }
        }

        return Optional.empty();
    }

    private User idRemovedVerification(Integer idUser){

        return userRepository.findByIdAndRemoved(idUser, false)
                .orElseThrow(() -> new NotFoundException(USER_NOT_EXIST_ID));
    }

    private void dniVerification(String dni, Integer idUser){

        if (userRepository.findByDniAndId(dni, idUser).isPresent()) {
            throw new UserAlreadyExistsException(NOT_UPDATED_USER);
        }
    }
}
