package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.Bill;
import edu.utn.utnPhones.models.PhoneLine;
import edu.utn.utnPhones.models.projections.BillsForUsers;
import edu.utn.utnPhones.models.projections.BillsWithoutPhoneCalls;
import edu.utn.utnPhones.services.BillService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BillControllerTest {

    BillController billController;

    @Mock
    BillService billService;

    @Before
    public void setUp(){
        initMocks(this);
        this.billController = new BillController(billService);
    }

    @Test
    public void getBillsOk(){
        when(billService.getBills()).thenReturn(new ArrayList<>());

        Assert.assertEquals(new ArrayList<BillsWithoutPhoneCalls>(), billController.getBills());
    }

    @Test
    public void getBillsByDateRangeOk(){
        when(billService.getBillsByDateRange(1, Date.from(Instant.now()), Date.from(Instant.now()))).thenReturn(new ArrayList<>());

        Assert.assertEquals(new ArrayList<BillsForUsers>(), billController.getBillsByDateRange(1, Date.from(Instant.now()), Date.from(Instant.now())));
    }

    public List<BillsForUsers> getBillsByDateRange(Integer idUser, Date date1, Date date2){

        return billService.getBillsByDateRange(idUser, date1, date2);
    }

    @Test
    public void getBillByIdOk(){
        when(billService.getBillById(1)).thenReturn(new Bill());

        Assert.assertEquals(new Bill(), billController.getBillById(1));
    }

    @Test
    public void payBillOk(){
        when(billService.payBill(1)).thenReturn(new Bill());

        Assert.assertEquals(new Bill(), billController.payBill(1));
    }
}
