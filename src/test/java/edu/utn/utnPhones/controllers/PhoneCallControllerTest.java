package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.projections.CallsByDateRange;
import edu.utn.utnPhones.models.projections.CallsByUser;
import edu.utn.utnPhones.models.projections.MostCalledDestination;
import edu.utn.utnPhones.services.PhoneCallService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneCallControllerTest implements FactoryController{

    PhoneCallController phoneCallController;

    @Mock
    PhoneCallService phoneCallService;

    @Before
    public void setUp(){
        initMocks(this);
        this.phoneCallController = new PhoneCallController(phoneCallService);
    }

    @Test
    public void getCallsByDateRangeOk(){
        when(phoneCallService.getCallsByDateRange(1, Date.from(Instant.now()), Date.from(Instant.now()))).thenReturn(new ArrayList<>());

        Assert.assertEquals(new ArrayList<CallsByDateRange>(), phoneCallController.getCallsByDateRange(1, Date.from(Instant.now()), Date.from(Instant.now())));
    }

    @Test
    public void getMostCalledDestinationsOk(){
        when(phoneCallService.getMostCalledDestinations(1)).thenReturn(new ArrayList<>());

        Assert.assertEquals(new ArrayList<MostCalledDestination>(), phoneCallController.getMostCalledDestinations(1));
    }

    @Test
    public void getByUserOk(){
        when(phoneCallService.getByUser(1)).thenReturn(new ArrayList<>());

        Assert.assertEquals(new ArrayList<CallsByUser>(), phoneCallController.getByUser(1));
    }

    @Test
    public void addOk(){
        when(phoneCallService.add(createPhoneCallAddDto())).thenReturn(createPhoneCall());

        Assert.assertEquals(createPhoneCall(), phoneCallController.add(createPhoneCallAddDto()));
    }
}
