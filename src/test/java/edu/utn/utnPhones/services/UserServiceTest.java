package edu.utn.utnPhones.services;

import edu.utn.utnPhones.exceptions.AlreadyExistsException;
import edu.utn.utnPhones.exceptions.DuplicatedUsernameException;
import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.exceptions.UnauthorizedUserTypeException;
import edu.utn.utnPhones.models.City;
import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.dtos.requests.UserDtoAdd;
import edu.utn.utnPhones.models.dtos.requests.UserDtoPatch;
import edu.utn.utnPhones.models.dtos.requests.UserDtoPut;
import edu.utn.utnPhones.models.enums.UserType;
import edu.utn.utnPhones.models.projections.ClientsWithoutPassword;
import edu.utn.utnPhones.repositories.CityRepository;
import edu.utn.utnPhones.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest implements FactoryService {

    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PhoneLineService phoneLineService;

    @Mock
    CityRepository cityRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Before
    public void setUp(){

        initMocks(this);
        userService = new UserService(userRepository, phoneLineService, cityRepository, passwordEncoder);
    }

    @Test
    public void testGetClientOk(){

        User fakeUser = createUser("Euvenias");
        when(userRepository.findByUserNameAndRemovedAndUserType("Euvenias", false, UserType.client)).thenReturn(Optional.of(fakeUser));
        User user = userService.getClient("Euvenias");
        Assert.assertEquals(fakeUser.getId(), user.getId());
    }

    @Test(expected = NotFoundException.class)
    public void testGetClientNotFound(){

        when(userRepository.findByUserNameAndRemovedAndUserType("FabiontsPR", false, UserType.client)).thenReturn(Optional.empty());
        userService.getClient("FabiontsPR");
    }

    @Test
    public void testGetClientsOk(){

        when(userRepository.findByUserTypeAndRemoved(UserType.client, false)).thenReturn(new ArrayList<>());
        Assert.assertEquals(new ArrayList<ClientsWithoutPassword>(), userService.getClients());
    }

    @Test
    public void testAdd() {

        UserDtoAdd userDtoAdd = createUserDtoAdd();
        User user = createUser();

        when(cityRepository.findByNameAndAreaCodeAndProvince("Mar del Plata", "223", "Buenos Aires")).thenReturn(Optional.of(new City()));
        when(userRepository.findByUserNameAndRemoved("fabiolaguna", false)).thenReturn(Optional.empty());
        when(userRepository.findByDni("38054312")).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.getIdByUserName("fabiolaguna", false)).thenReturn(1);

        Assert.assertEquals(user, userService.add(userDtoAdd));
    }

    @Test(expected = DuplicatedUsernameException.class)
    public void testAddDuplicatedUserName() {

        UserDtoAdd userDtoAdd = createUserDtoAdd();

        when(cityRepository.findByNameAndAreaCodeAndProvince("Mar del Plata", "223", "Buenos Aires")).thenReturn(Optional.of(new City()));
        when(userRepository.findByUserNameAndRemoved("fabiolaguna", false)).thenReturn(Optional.of(createUser()));

        userService.add(userDtoAdd);
    }

    @Test(expected = AlreadyExistsException.class)
    public void testAddDniExistent() {

        User user = createUser();

        when(cityRepository.findByNameAndAreaCodeAndProvince("Mar del Plata", "223", "Buenos Aires")).thenReturn(Optional.of(new City()));
        when(userRepository.findByUserNameAndRemoved("fabiolaguna", false)).thenReturn(Optional.empty());
        when(userRepository.findByDni("38054312")).thenReturn(user);

        userService.add(createUserDtoAdd());
    }

    @Test
    public void testAddDniExistentRemoved() {

        UserDtoAdd userDtoAdd = createUserDtoAdd();
        User user = createUser();
        User userRemoved = createUser();
        userRemoved.setRemoved(true);
        userRemoved.setId(3);

        when(cityRepository.findByNameAndAreaCodeAndProvince("Mar del Plata", "223", "Buenos Aires")).thenReturn(Optional.of(new City()));
        when(userRepository.findByUserNameAndRemoved("fabiolaguna", false)).thenReturn(Optional.empty());
        when(userRepository.findByDni(userDtoAdd.getDni())).thenReturn(userRemoved);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.getIdByUserName("fabiolaguna", false)).thenReturn(1);

        Assert.assertEquals(user, userService.add(userDtoAdd));
    }

    @Test
    public void testRemove(){

        when(userRepository.findByIdAndRemoved(1, false)).thenReturn(Optional.of(createUser()));
        when(userRepository.save(any(User.class))).thenReturn(createUser());

        userService.remove(1);
    }

    @Test
    public void testUpdate(){

        UserDtoPut userDto = createUserDtoPut();
        User oldUser = createUser();

        when(userRepository.findByIdAndRemoved(1, false)).thenReturn(Optional.of(oldUser));
        when(userRepository.findByDniAndId("40000000", 1)).thenReturn(Optional.empty());
        when(userRepository.findByIdAndUserNameAndRemoved("hola1234", false, 1)).thenReturn(Optional.empty());
        when(cityRepository.findByNameAndAreaCodeAndProvince("Mar del Plata", "223", "Buenos Aires")).thenReturn(Optional.of(createCity()));
        when(userRepository.save(any(User.class))).thenReturn(oldUser);

        Assert.assertEquals(oldUser, userService.update(1, userDto));
    }

    @Test(expected = DuplicatedUsernameException.class)
    public void testUpdateDuplicatedUserName(){

        UserDtoPut userDto = createUserDtoPut();
        User oldUser = createUser();

        when(userRepository.findByIdAndRemoved(1, false)).thenReturn(Optional.of(oldUser));
        when(userRepository.findByDniAndId("40000000", 1)).thenReturn(Optional.empty());
        when(userRepository.findByIdAndUserNameAndRemoved("hola1234", false, 1)).thenReturn(Optional.of(oldUser));
        when(cityRepository.findByNameAndAreaCodeAndProvince("Mar del Plata", "223", "Buenos Aires")).thenReturn(Optional.of(createCity()));
        when(userRepository.save(any(User.class))).thenReturn(oldUser);

        userService.update(1, userDto);
    }

    @Test(expected = UnauthorizedUserTypeException.class)
    public void testUpdateUnauthorizedUserType(){

        User oldUser = createUser();
        oldUser.setUserType(UserType.infrastructure);

        when(userRepository.findByIdAndRemoved(1, false)).thenReturn(Optional.of(oldUser));

        userService.update(1, createUserDtoPut());
    }

    @Test(expected = AlreadyExistsException.class)
    public void testUpdateDniAlreadyExist(){

        UserDtoPut userDto = createUserDtoPut();
        User oldUser = createUser();

        when(userRepository.findByIdAndRemoved(1, false)).thenReturn(Optional.of(oldUser));
        when(userRepository.findByDniAndId(userDto.getDni(), 1)).thenReturn(Optional.of(oldUser));

        userService.update(1, userDto);
    }

    @Test
    public void testPartialUpdate(){

        UserDtoPatch userDto = createUserDtoPatch();
        User oldUser = createUser();

        when(userRepository.findByIdAndRemoved(1, false)).thenReturn(Optional.of(oldUser));
        when(userRepository.findByDniAndId("40000000", 1)).thenReturn(Optional.empty());
        when(userRepository.findByIdAndUserNameAndRemoved("hola1234", false, 1)).thenReturn(Optional.empty());
        when(cityRepository.findByNameAndAreaCodeAndProvince("Mar del Plata", "223", "Buenos Aires")).thenReturn(Optional.of(createCity()));
        when(userRepository.save(any(User.class))).thenReturn(oldUser);

        Assert.assertEquals(oldUser, userService.partialUpdate(1, userDto));
    }

    @Test
    public void testLoginOk(){

        User user = createUser();

        when(userRepository.findByUserNameAndRemoved("fabiolaguna", false)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("12345678", user.getPwd())).thenReturn(true);

        Assert.assertEquals(user, userService.login("fabiolaguna", "12345678"));
    }

    @Test(expected = NotFoundException.class)
    public void testLoginFail(){

        User user = createUser();

        when(userRepository.findByUserNameAndRemoved("fabiolaguna", false)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("12345678", user.getPwd())).thenReturn(false);

        userService.login("fabiolaguna", "12345678");
    }

}
