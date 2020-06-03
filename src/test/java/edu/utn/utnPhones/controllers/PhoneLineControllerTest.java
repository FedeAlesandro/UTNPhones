package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.dtos.PhoneLineUserTestImpl;
import edu.utn.utnPhones.models.projections.PhoneLineUser;
import edu.utn.utnPhones.services.PhoneLineService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneLineControllerTest {

    PhoneLineController phoneLineController;

    @Mock
    PhoneLineService phoneLineService;

    @Before
    public void setUp(){
        initMocks(this);
        phoneLineController = new PhoneLineController(phoneLineService);
    }

    @Test
    public void testGetByUserNameEmpty(){
        List<PhoneLineUser> phoneLinesList = new ArrayList<>();
        when(phoneLineService.getByUserName("Hola")).thenReturn(phoneLinesList);

        List<PhoneLineUser> phoneLineListReturned = phoneLineController.getByUserName("Hola").getBody();
        Assert.assertTrue(phoneLineListReturned.isEmpty());
    }

    @Test
    public void testGetByUserNameOk(){
        List<PhoneLineUser> phoneLinesList = new ArrayList<>();
        phoneLinesList.add(new PhoneLineUserTestImpl());

        when(phoneLineService.getByUserName("Ramon")).thenReturn(phoneLinesList);
        List<PhoneLineUser> phoneLineListReturned = phoneLineController.getByUserName("Ramon").getBody();
        Assert.assertEquals(1, phoneLineListReturned.size());
    }
}
