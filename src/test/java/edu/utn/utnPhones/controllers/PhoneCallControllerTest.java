package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.PhoneCall;
import edu.utn.utnPhones.models.dtos.PhoneCallDtoResponse;
import edu.utn.utnPhones.models.projections.ClientsWithoutPassword;
import edu.utn.utnPhones.services.PhoneCallService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneCallControllerTest {

    PhoneCallController phoneCallController;

    @Mock
    PhoneCallService phoneCallService;

    @Before
    public void setup(){
        initMocks(this);
        phoneCallController = new PhoneCallController(phoneCallService);
    }

    @Test
    public void testGetByDurationOk(){
        List<PhoneCall> phoneCalls = new ArrayList<>();
        phoneCalls.add(PhoneCall.builder()
                    .id(1)
                    .duration(2)
                    .originPhoneNumber("2235123456")
                    .destinationPhoneNumber("2235654321")
                    .build());

        when(phoneCallService.getByDuration(1, 2)).thenReturn(phoneCalls);

        List<PhoneCallDtoResponse> phoneCallsResponse = phoneCallController.getByDuration(1, 2).getBody();

        Assert.assertFalse(phoneCallsResponse.isEmpty());
    }

    @Test
    public void testGetByDurationNoContent(){
        List<PhoneCall> phoneCalls = new ArrayList<>();
        when(phoneCallService.getByDuration(1, 2)).thenReturn(phoneCalls);

        List<PhoneCallDtoResponse> phoneCallsResponse = phoneCallController.getByDuration(1, 2).getBody();
        //tuve que hacerlo asi porque me retorna null
        if(phoneCallsResponse == null)
            phoneCallsResponse = new ArrayList<>();

        Assert.assertTrue(phoneCallsResponse.isEmpty());
    }
}
