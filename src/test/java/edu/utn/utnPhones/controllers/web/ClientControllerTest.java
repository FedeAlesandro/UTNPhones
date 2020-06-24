package edu.utn.utnPhones.controllers.web;

import edu.utn.utnPhones.controllers.BillController;
import edu.utn.utnPhones.controllers.FactoryController;
import edu.utn.utnPhones.controllers.PhoneCallController;
import edu.utn.utnPhones.models.projections.BillsForUsers;
import edu.utn.utnPhones.models.projections.CallsByDateRange;
import edu.utn.utnPhones.models.projections.MostCalledDestination;
import edu.utn.utnPhones.session.SessionManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClientControllerTest implements FactoryController {

    ClientController clientController;

    @Mock
    PhoneCallController phoneCallController;

    @Mock
    BillController billController;

    @Mock
    SessionManager sessionManager;

    @Before
    public void setUp(){
        initMocks(this);
        this.clientController = new ClientController(phoneCallController, billController, sessionManager);
    }

    @Test
    public void getMostCalledDestinationsNoContent(){
        List<MostCalledDestination> callsList = new ArrayList<>();
        when(sessionManager.getCurrentUser("123")).thenReturn(createUser());
        when(phoneCallController.getMostCalledDestinations(1)).thenReturn(callsList);

        Assert.assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).body(callsList), clientController.getCalls("123", null, null));
    }

    @Test
    public void getMostCalledDestinationsOk(){
        List<MostCalledDestination>callsList = new ArrayList<>();
        MostCalledDestination mostCalledDestinations = new SpelAwareProxyProjectionFactory().createProjection(MostCalledDestination.class);
        callsList.add(mostCalledDestinations);
        when(sessionManager.getCurrentUser("123")).thenReturn(createUser());
        when(phoneCallController.getMostCalledDestinations(1)).thenReturn(callsList);

        Assert.assertEquals(ResponseEntity.ok().body(callsList), clientController.getCalls("123", null, null));
    }

    @Test
    public void getCallsByDateRangeNoContent() {
        when(sessionManager.getCurrentUser("123")).thenReturn(createUser());
        when(phoneCallController.getCallsByDateRange(1, Date.from(Instant.now()), Date.from(Instant.now()))).thenReturn(new ArrayList<>());

        Assert.assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>()), clientController.getCalls("123", Date.from(Instant.now()), Date.from(Instant.now())));
    }

    @Test
    public void getCallsByDateRangeOk() {
        List<CallsByDateRange>callsList = new ArrayList<>();
        CallsByDateRange callsByDateRange = new SpelAwareProxyProjectionFactory().createProjection(CallsByDateRange.class);
        callsList.add(callsByDateRange);
        when(sessionManager.getCurrentUser("123")).thenReturn(createUser());
        when(phoneCallController.getCallsByDateRange(anyInt(), any(Date.class),  any(Date.class))).thenReturn(callsList);

        Assert.assertEquals(ResponseEntity.ok().body(callsList), clientController.getCalls("123", Date.from(Instant.now()), Date.from(Instant.now())));
    }

    @Test
    public void getCallsByDateRangeBadRequest() {
        when(sessionManager.getCurrentUser("123")).thenReturn(createUser());

        Assert.assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).build(), clientController.getCalls("123", null, Date.from(Instant.now())));
    }

    @Test
    public void getBillsNoContent(){
        when(sessionManager.getCurrentUser("123")).thenReturn(createUser());
        when(billController.getBillsByDateRange(1, Date.from(Instant.now()), Date.from(Instant.now()))).thenReturn(new ArrayList<>());

        Assert.assertEquals(ResponseEntity.noContent().build(), clientController.getBills("123", Date.from(Instant.now()), Date.from(Instant.now())));
    }

    @Test
    public void getBillsOk(){
        List<BillsForUsers> bills = new ArrayList<>();
        BillsForUsers billsForUsers = new SpelAwareProxyProjectionFactory().createProjection(BillsForUsers.class);
        bills.add(billsForUsers);

        when(sessionManager.getCurrentUser("123")).thenReturn(createUser());
        when(billController.getBillsByDateRange(anyInt(), any(Date.class),  any(Date.class))).thenReturn(bills);

        Assert.assertEquals(ResponseEntity.ok(bills), clientController.getBills("123", Date.from(Instant.now()), Date.from(Instant.now())));
    }
}
