package edu.utn.utnPhones.services;

import edu.utn.utnPhones.models.PhoneCall;
import edu.utn.utnPhones.models.dtos.PhoneCallDtoResponse;
import edu.utn.utnPhones.repositories.PhoneCallRepository;
import edu.utn.utnPhones.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneCallServiceTest {

    PhoneCallService phoneCallService;

    @Mock
    PhoneCallRepository phoneCallRepository;

    @Mock
    UserRepository userRepository;

    @Before
    public void setup(){
        initMocks(this);
        phoneCallService = new PhoneCallService(phoneCallRepository, userRepository);
    }

    @Test
    public void testGetByDurationContent(){
        List<PhoneCall> phoneCalls = new ArrayList<>();
        phoneCalls.add(PhoneCall.builder()
                .id(1)
                .duration(2)
                .originPhoneNumber("2235123456")
                .destinationPhoneNumber("2235654321")
                .build());

        when(phoneCallRepository.getByDuration(1, 2)).thenReturn(phoneCalls);

        List<PhoneCall> phoneCallsResponse = phoneCallService.getByDuration(1, 2);

        Assert.assertFalse(phoneCallsResponse.isEmpty());
    }

    @Test
    public void testGetByDurationEmpty(){
        List<PhoneCall> phoneCalls = new ArrayList<>();
        when(phoneCallRepository.getByDuration(1, 2)).thenReturn(phoneCalls);

        List<PhoneCall> phoneCallsResponse = phoneCallService.getByDuration(1, 2);

        Assert.assertTrue(phoneCalls.isEmpty());
    }
}
