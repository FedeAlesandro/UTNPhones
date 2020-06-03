package edu.utn.utnPhones.services;

import edu.utn.utnPhones.models.dtos.PhoneLineUserTestImpl;
import edu.utn.utnPhones.models.projections.PhoneLineUser;
import edu.utn.utnPhones.repositories.PhoneLineRepository;
import edu.utn.utnPhones.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneLineServiceTest {

    PhoneLineService phoneLineService;

    @Mock
    PhoneLineRepository phoneLineRepository;

    @Mock
    UserRepository userRepository;

    @Before
    public void setUp(){
        initMocks(this);
        phoneLineService = new PhoneLineService(phoneLineRepository, userRepository);
    }

    @Test
    public void testGetByUserNameEmpty(){
        List<PhoneLineUser> phoneLinesList = new ArrayList<>();
        when(phoneLineRepository.getByUserName("Chau")).thenReturn(phoneLinesList);

        List<PhoneLineUser> phoneLineListReturned = phoneLineService.getByUserName("Chau");
        Assert.assertTrue(phoneLineListReturned.isEmpty());
    }

    @Test
    public void testGetByUserNameOk(){
        List<PhoneLineUser> phoneLinesList = new ArrayList<>();
        phoneLinesList.add(new PhoneLineUserTestImpl());

        when(phoneLineRepository.getByUserName("Ramon")).thenReturn(phoneLinesList);
        List<PhoneLineUser> phoneLineListReturned = phoneLineService.getByUserName("Ramon");
        Assert.assertEquals(1, phoneLineListReturned.size());
    }
}
