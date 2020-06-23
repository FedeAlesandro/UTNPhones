package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.PhoneLine;
import edu.utn.utnPhones.services.PhoneLineService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneLineControllerTest implements FactoryController{

    PhoneLineController phoneLineController;

    @Mock
    PhoneLineService phoneLineService;

    @Before
    public void setUp(){
        initMocks(this);
        phoneLineController = new PhoneLineController(phoneLineService);
    }

    @Test
    public void getAllOk(){
        when(phoneLineService.getAll()).thenReturn(new ArrayList<>());

        Assert.assertEquals(new ArrayList<PhoneLine>(), phoneLineController.getAll());
    }

    @Test
    public void getByUserNameOk(){
        when(phoneLineService.getByUserName("Euvenias")).thenReturn(new ArrayList<>());

        Assert.assertEquals(new ArrayList<PhoneLine>(), phoneLineController.getByUserName("Euvenias"));
    }

    @Test
    public void addOk(){
        when(phoneLineService.add(createPhoneLineDtoAdd())).thenReturn(createPhoneLine());

        Assert.assertEquals(createPhoneLine(), phoneLineController.add(createPhoneLineDtoAdd()));
    }

    @Test
    public void updateOk(){
        when(phoneLineService.update(1, createPhoneLineDtoUpdate())).thenReturn(createPhoneLine());

        Assert.assertEquals(createPhoneLine(), phoneLineController.update(1, createPhoneLineDtoUpdate()));
    }

    @Test
    public void removeOk(){
        doNothing().when(phoneLineService).remove(1);

        phoneLineController.remove(1);
    }
}
