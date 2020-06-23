package edu.utn.utnPhones.services;

import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.models.Tariff;
import edu.utn.utnPhones.repositories.TariffRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TariffServiceTest {

    private TariffService tariffService;

    @Mock
    private TariffRepository tariffRepository;

    @Before
    public void setUp(){
        initMocks(this);
        this.tariffService = new TariffService(tariffRepository);
    }

    @Test
    public void getAll() {
        when(tariffRepository.findAll()).thenReturn(new ArrayList<Tariff>());

        Assert.assertEquals(new ArrayList<Tariff>(), tariffService.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void getByIdNotFoundException(){
        when(tariffRepository.findById(1)).thenReturn(Optional.empty());

        tariffService.getById(1);
    }

    @Test
    public void getByIdOk(){
        when(tariffRepository.findById(1)).thenReturn(Optional.of(new Tariff()));

        tariffService.getById(1);
    }
}
