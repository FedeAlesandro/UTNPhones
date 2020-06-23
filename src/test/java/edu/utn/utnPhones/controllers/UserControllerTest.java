package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.projections.ClientsWithoutPassword;
import edu.utn.utnPhones.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserControllerTest implements FactoryController{

    UserController userController;

    @Mock
    UserService userService;

    @Before
    public void setUp(){
        initMocks(this);
        userController = new UserController(userService);
    }

    @Test
    public void getClientsOk(){
        when(userService.getClients()).thenReturn(new ArrayList<>());

        Assert.assertEquals(new ArrayList<ClientsWithoutPassword>(), userController.getClients());
    }

    @Test
    public void getClientOk(){
        when(userService.getClient("Euvenias")).thenReturn(new User());

        Assert.assertEquals(new User(), userController.getClient("Euvenias"));
    }

    @Test
    public void addOk(){
        when(userService.add(createUserDtoAdd())).thenReturn(new User());

        Assert.assertEquals(new User(), userController.add(createUserDtoAdd()));
    }

    @Test
    public void removeOk(){
        doNothing().when(userService).remove(1);

        userController.remove(1);
    }

    @Test
    public void updateOk(){
        when(userService.update(1, createUserDtoPut())).thenReturn(new User());

        Assert.assertEquals(new User(), userController.update(1, createUserDtoPut()));
    }

    @Test
    public void partialUpdateOk(){
        when(userService.partialUpdate(1, createUserDtoPatch())).thenReturn(new User());

        Assert.assertEquals(new User(), userController.partialUpdate(1, createUserDtoPatch()));
    }

    @Test
    public void loginOk(){
        when(userService.login("Euvenias", "1234")).thenReturn(new User());

        Assert.assertEquals(new User(), userController.login("Euvenias", "1234"));
    }
}
