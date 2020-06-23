package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.Tariff;
import edu.utn.utnPhones.services.TariffService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TariffControllerTest {

    TariffController tariffController;

    @Mock
    TariffService tariffService;

    @Before
    public void setUp(){
        initMocks(this);
        tariffController = new TariffController(tariffService);
    }

    @Test
    public void getAllOk(){
        when(tariffService.getAll()).thenReturn(new ArrayList<>());

        Assert.assertEquals(new ArrayList<Tariff>(), tariffController.getAll());
    }

    @Test
    public void getByIdOk(){
        when(tariffService.getById(1)).thenReturn(new Tariff());

        Assert.assertEquals(new Tariff(), tariffController.getById(1));
    }
}
