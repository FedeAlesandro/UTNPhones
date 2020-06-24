package edu.utn.utnPhones.controllers.web;

import edu.utn.utnPhones.controllers.FactoryController;
import edu.utn.utnPhones.controllers.UserController;
import edu.utn.utnPhones.models.dtos.requests.LoginDto;
import edu.utn.utnPhones.session.Session;
import edu.utn.utnPhones.session.SessionManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.time.Instant;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LoginControllerTest implements FactoryController {

    LoginController loginController;

    @Mock
    UserController userController;

    @Mock
    SessionManager sessionManager;

    @Before
    public void setUp(){
        initMocks(this);
        this.loginController = new LoginController(userController, sessionManager);
    }

    @Test
    public void loginTest(){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", "1234");

        when(userController.login("Euvenias", "hola")).thenReturn(createUser());
        when(sessionManager.createSession(createUser())).thenReturn("1234");

        Assert.assertEquals(ResponseEntity.ok().headers(responseHeaders).build(), loginController.login(new LoginDto("Euvenias", "hola")));
    }

    @Test
    public void logoutForbidden(){
        when(sessionManager.getSession("1234")).thenReturn(null);

        Assert.assertEquals(ResponseEntity.status(HttpStatus.FORBIDDEN).build(), loginController.logout("1234"));
    }

    @Test
    public void logoutOk(){
        when(sessionManager.getSession("1234")).thenReturn(new Session("1234", createUser(), Date.from(Instant.now())));

        Assert.assertEquals(ResponseEntity.ok().build(), loginController.logout("1234"));
    }
}
