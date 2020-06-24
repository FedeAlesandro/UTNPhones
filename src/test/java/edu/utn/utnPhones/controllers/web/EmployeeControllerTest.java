package edu.utn.utnPhones.controllers.web;

import edu.utn.utnPhones.controllers.BillController;
import edu.utn.utnPhones.controllers.FactoryController;
import edu.utn.utnPhones.controllers.PhoneCallController;
import edu.utn.utnPhones.controllers.PhoneLineController;
import edu.utn.utnPhones.controllers.TariffController;
import edu.utn.utnPhones.controllers.UserController;
import edu.utn.utnPhones.models.PhoneLine;
import edu.utn.utnPhones.models.Tariff;
import edu.utn.utnPhones.models.dtos.responses.BillDtoResponse;
import edu.utn.utnPhones.models.dtos.responses.PhoneLineDtoResponse;
import edu.utn.utnPhones.models.dtos.responses.TariffDtoResponse;
import edu.utn.utnPhones.models.dtos.responses.UserDtoResponse;
import edu.utn.utnPhones.models.projections.BillsWithoutPhoneCalls;
import edu.utn.utnPhones.models.projections.CallsByUser;
import edu.utn.utnPhones.models.projections.ClientsWithoutPassword;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class EmployeeControllerTest implements FactoryController {

    EmployeeController employeeController;

    @Mock
    UserController userController;

    @Mock
    PhoneCallController phoneCallController;

    @Mock
    PhoneLineController phoneLineController;

    @Mock
    BillController billController;

    @Mock
    TariffController tariffController;

    @Before
    public void setUp(){
        initMocks(this);
        this.employeeController = new EmployeeController(userController, phoneCallController, phoneLineController, billController, tariffController);
    }

    @Test
    public void getClientsNoContent(){
        when(userController.getClients()).thenReturn(new ArrayList<>());

        Assert.assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<ClientsWithoutPassword>()), employeeController.getClients(null));
    }

    @Test
    public void getClientsOk(){
        List<ClientsWithoutPassword> clientsList = new ArrayList<>();
        ClientsWithoutPassword client = new SpelAwareProxyProjectionFactory().createProjection(ClientsWithoutPassword.class);
        clientsList.add(client);

        when(userController.getClients()).thenReturn(clientsList);

        Assert.assertEquals(ResponseEntity.ok().body(clientsList), employeeController.getClients(null));
    }

    @Test
    public void getClient(){
        when(userController.getClient("Euvenias")).thenReturn(createUser());

        Assert.assertEquals(ResponseEntity.ok().body(UserDtoResponse.fromUser(createUser())), employeeController.getClients("Euvenias"));
    }

    @Test
    public void removeUserOk(){
        doNothing().when(userController).remove(1);

        Assert.assertEquals(ResponseEntity.ok().build(), employeeController.removeUser(1));
    }

    @Test
    public void updateUserOk(){
        when(userController.update(1, createUserDtoPut())).thenReturn(createUser());

        Assert.assertEquals(ResponseEntity.ok(UserDtoResponse.fromUser(createUser())), employeeController.updateUser(1, createUserDtoPut()));
    }

    @Test
    public void partialUpdateUserOk(){
        when(userController.partialUpdate(1, createUserDtoPatch())).thenReturn(createUser());

        Assert.assertEquals(ResponseEntity.ok(UserDtoResponse.fromUser(createUser())), employeeController.partialUpdateUser(1, createUserDtoPatch()));
    }

    @Test
    public void getCallsByUserNoContent(){
        when(phoneCallController.getByUser(1)).thenReturn(new ArrayList<>());

        Assert.assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<CallsByUser>()), employeeController.getCallsByUser(1));
    }

    @Test
    public void getCallsByUserOk(){
        List<CallsByUser> callsList = new ArrayList<>();
        CallsByUser call = new SpelAwareProxyProjectionFactory().createProjection(CallsByUser.class);
        callsList.add(call);

        when(phoneCallController.getByUser(1)).thenReturn(callsList);

        Assert.assertEquals(ResponseEntity.ok(callsList), employeeController.getCallsByUser(1));
    }

    @Test
    public void updatePhoneLineOk(){
        when(phoneLineController.update(1, createPhoneLineDtoUpdate())).thenReturn(createPhoneLine());

        Assert.assertEquals(ResponseEntity.ok(PhoneLineDtoResponse.fromPhoneLine(createPhoneLine())), employeeController.updatePhoneLine(1, createPhoneLineDtoUpdate()));
    }

    @Test
    public void removePhoneLineOk(){
        doNothing().when(phoneLineController).remove(1);

        Assert.assertEquals(ResponseEntity.ok().build(), employeeController.removePhoneLine(1));
    }

    @Test
    public void getAllNoContent(){
        when(phoneLineController.getAll()).thenReturn(new ArrayList<>());

        Assert.assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<PhoneLineDtoResponse>()), employeeController.getPhoneLines(null));
    }

    @Test
    public void getAllOk(){
        List<PhoneLine> phoneLines = new ArrayList<>();
        phoneLines.add(createPhoneLine());
        List<PhoneLineDtoResponse> phoneLinesResponses = new ArrayList<>();
        phoneLinesResponses.add(PhoneLineDtoResponse.fromPhoneLine(createPhoneLine()));

        when(phoneLineController.getAll()).thenReturn(phoneLines);

        Assert.assertEquals(ResponseEntity.ok(phoneLinesResponses), employeeController.getPhoneLines(null));
    }

    @Test
    public void getByUserNameNoContent(){
        when(phoneLineController.getByUserName("Euvenias")).thenReturn(new ArrayList<>());

        Assert.assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<PhoneLineDtoResponse>()), employeeController.getPhoneLines("Euvenias"));
    }

    @Test
    public void getByUserNameOk(){
        List<PhoneLine> phoneLines = new ArrayList<>();
        phoneLines.add(createPhoneLine());
        List<PhoneLineDtoResponse> phoneLinesResponses = new ArrayList<>();
        phoneLinesResponses.add(PhoneLineDtoResponse.fromPhoneLine(createPhoneLine()));

        when(phoneLineController.getByUserName("Euvenias")).thenReturn(phoneLines);

        Assert.assertEquals(ResponseEntity.ok(phoneLinesResponses), employeeController.getPhoneLines("Euvenias"));
    }

    @Test
    public void getBillIdOk(){
        when(billController.getBillById(1)).thenReturn(createBill());

        Assert.assertEquals(ResponseEntity.ok(BillDtoResponse.fromBill(createBill(), new ArrayList<>())), employeeController.getBills(1));
    }

    @Test
    public void getBillsNoContent(){
        when(billController.getBills()).thenReturn(new ArrayList<>());

        Assert.assertEquals(ResponseEntity.noContent().build(), employeeController.getBills(null));
    }

    @Test
    public void getBillsOk(){
        List<BillsWithoutPhoneCalls> bills = new ArrayList<>();
        BillsWithoutPhoneCalls bill = new SpelAwareProxyProjectionFactory().createProjection(BillsWithoutPhoneCalls.class);
        bills.add(bill);

        when(billController.getBills()).thenReturn(bills);

        Assert.assertEquals(ResponseEntity.ok(bills), employeeController.getBills(null));
    }

    @Test
    public void payBillOk(){
        when(billController.payBill(1)).thenReturn(createBill());

        Assert.assertEquals(ResponseEntity.ok(BillDtoResponse.fromBill(createBill(), new ArrayList<>())), employeeController.payBill(1));
    }

    @Test
    public void getAllTariffsNoContent(){
        when(tariffController.getAll()).thenReturn(new ArrayList<>());

        Assert.assertEquals(ResponseEntity.noContent().build(), employeeController.getTariffs(null));
    }

    @Test
    public void getAllTariffsOk(){
        List<Tariff> tariffs = new ArrayList<>();
        tariffs.add(createTariff());

        List<TariffDtoResponse> tariffResponses = new ArrayList<>();
        tariffResponses.add(TariffDtoResponse.fromTariff(createTariff()));

        when(tariffController.getAll()).thenReturn(tariffs);

        Assert.assertEquals(ResponseEntity.ok(tariffResponses), employeeController.getTariffs(null));
    }

    @Test
    public void getTariffByIdOk(){
        when(tariffController.getById(1)).thenReturn(createTariff());

        Assert.assertEquals(ResponseEntity.ok(TariffDtoResponse.fromTariff(createTariff())), employeeController.getTariffs(1));
    }
}
