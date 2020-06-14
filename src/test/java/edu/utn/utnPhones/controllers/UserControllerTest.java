package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.models.City;
import edu.utn.utnPhones.models.Province;
import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.enums.UserType;
import edu.utn.utnPhones.models.projections.implementations.ClientsWithoutPasswordTestImpl;
import edu.utn.utnPhones.models.projections.ClientsWithoutPassword;
import edu.utn.utnPhones.services.UserService;
import edu.utn.utnPhones.utils.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserControllerTest {

    UserController userController;

    @Mock
    UserService userService;

    @Before
    public void setUp(){
        initMocks(this);
        userController = new UserController(userService);
    }

    @Test
    public void testGetClientsEmpty(){

        List<ClientsWithoutPassword> clientsList = new ArrayList<>();
        when(userService.getClients()).thenReturn(clientsList);
        Assert.assertTrue(clientsList.isEmpty());
    }

    @Test
    public void testGetClientsOk(){
        List<ClientsWithoutPassword> clientsList = new ArrayList<>();
        clientsList.add(new ClientsWithoutPasswordTestImpl());
        when(userService.getClients()).thenReturn(clientsList);
        Assert.assertFalse(clientsList.isEmpty());
    }

    @Test
    public void testGetClientOk(){
        Province province = new Province(1, "Buenos Aires");
        User oldUser = User.builder()
                .id(1)
                .city(new City(1, province, "Mar del Plata", "223"))
                .name("Fede")
                .lastName("Fede")
                .dni("12345678")
                .userName("Euvenias")
                .pwd("pwd")
                .userType(UserType.client)
                .removed(false)
                .build();

        when(userService.getClient("Euvenias")).thenReturn(oldUser);
        User user = userController.getClient("Euvenias");
        Assert.assertEquals(oldUser.getId(), user.getId());
    }

    @Test(expected = NotFoundException.class)
    public void testGetClientNotFound(){
        when(userService.getClient("Euvenias")).thenThrow(new NotFoundException(Constants.USER_NOT_EXIST_USERNAME));
        userController.getClient("Euvenias");
    }
}
