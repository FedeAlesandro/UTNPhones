package edu.utn.utnPhones.services;

import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.models.PhoneCall;
import edu.utn.utnPhones.models.dtos.requests.PhoneCallDtoAdd;
import edu.utn.utnPhones.models.projections.CallsByDateRange;
import edu.utn.utnPhones.models.projections.CallsByUser;
import edu.utn.utnPhones.models.projections.MostCalledDestination;
import edu.utn.utnPhones.repositories.PhoneCallRepository;
import edu.utn.utnPhones.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneCallServiceTest implements FactoryService {

    PhoneCallService phoneCallService;

    @Mock
    PhoneCallRepository phoneCallRepository;

    @Mock
    UserRepository userRepository;

    @Before
    public void setUp(){

        initMocks(this);
        phoneCallService = new PhoneCallService(phoneCallRepository, userRepository);
    }

    @Test
    public void testGetCallsByDateRangeOk(){

        when(userRepository.existsById(1)).thenReturn(true);
        when(phoneCallRepository.getCallsByDateRange(1, Date.from(Instant.now()), Date.from(Instant.now()))).thenReturn(new ArrayList<CallsByDateRange>());

        Assert.assertEquals(new ArrayList<CallsByDateRange>(), phoneCallService.getCallsByDateRange(1, Date.from(Instant.now()), Date.from(Instant.now())));
    }

    @Test(expected = NotFoundException.class)
    public void testGetCallsByDateRangeUserNotFound(){

        when(userRepository.existsById(1)).thenReturn(false);
        phoneCallService.getCallsByDateRange(1, Date.from(Instant.now()), Date.from(Instant.now()));
    }

    @Test
    public void testGetMostCalledDestinationsOk(){

        when(userRepository.existsById(1)).thenReturn(true);
        when(phoneCallRepository.getMostCalledDestinations(1)).thenReturn(new ArrayList<MostCalledDestination>());

        Assert.assertEquals(new ArrayList<MostCalledDestination>(), phoneCallService.getMostCalledDestinations(1));
    }

    @Test(expected = NotFoundException.class)
    public void testGetMostCalledDestinationsUserNotFound(){

        when(userRepository.existsById(1)).thenReturn(false);
        phoneCallService.getMostCalledDestinations(1);
    }

    @Test
    public void getByUserOk() {

        when(userRepository.existsById(1)).thenReturn(true);
        when(phoneCallRepository.getByUser(1)).thenReturn(new ArrayList<CallsByUser>());
        Assert.assertEquals(new ArrayList<CallsByUser>(), phoneCallService.getByUser(1));
    }

    @Test(expected = NotFoundException.class)
    public void getByUserNotFound() {

        when(userRepository.existsById(1)).thenReturn(false);
        phoneCallService.getByUser(1);
    }

    @Test
    public void testAdd(){

        when(phoneCallRepository.save(createPhoneCall())).thenReturn(createPhoneCall());
        when(phoneCallRepository.getDateById(1)).thenReturn(Date.from(Instant.now()));

        Assert.assertEquals(createPhoneCall(), phoneCallService.add(createPhoneCallAddDto()));
    }

    public PhoneCall add(PhoneCallDtoAdd phoneCall) {

        PhoneCall phoneCallToAdd = PhoneCall.fromDto(phoneCall);

        phoneCallToAdd = phoneCallRepository.save(phoneCallToAdd);

        phoneCallToAdd.setDate(phoneCallRepository.getDateById(phoneCallToAdd.getId()));

        return phoneCallToAdd;
    }
}
