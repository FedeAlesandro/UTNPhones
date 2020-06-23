package edu.utn.utnPhones.services;

import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.models.City;
import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.dtos.requests.UserDtoAdd;
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
import java.util.List;
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
    public void testGetClientsEmpty(){

        List<ClientsWithoutPassword> fakeClientsList = new ArrayList<>();
        when(userRepository.findByUserTypeAndRemoved(UserType.client, false)).thenReturn(fakeClientsList);
        List<ClientsWithoutPassword> clientsList = userService.getClients();
        Assert.assertTrue(clientsList.isEmpty());
    }

    @Test
    public void testGetClientsOk(){

        List<ClientsWithoutPassword> fakeClientsList = createClientsWithoutPassword();
        when(userRepository.findByUserTypeAndRemoved(UserType.client, false)).thenReturn(fakeClientsList);
        List<ClientsWithoutPassword> clientsList = userService.getClients();
        Assert.assertFalse(clientsList.isEmpty());
    }

    @Test
    public void testAddOk() {

        UserDtoAdd userDtoAdd = createUserDtoAdd();
        User user = createUser();

        when(cityRepository.findByNameAndAreaCodeAndProvince("Mar del Plata", "223", "Buenos Aires")).thenReturn(Optional.of(new City()));
        when(userRepository.findByUserNameAndRemoved("fabiolaguna", false)).thenReturn(Optional.empty());
        when(userRepository.findByDni("38054312")).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.getIdByUserName("fabiolaguna", false)).thenReturn(1);

        Assert.assertEquals(user, userService.add(userDtoAdd));
    }

}
