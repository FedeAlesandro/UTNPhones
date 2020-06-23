package edu.utn.utnPhones.services;

import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.models.Bill;
import edu.utn.utnPhones.models.projections.BillsForUsers;
import edu.utn.utnPhones.models.projections.BillsWithoutPhoneCalls;
import edu.utn.utnPhones.repositories.BillRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BillServiceTest implements FactoryService{

    private BillService billService;

    @Mock
    private BillRepository billRepository;

    @Before
    public void setUp(){
        initMocks(this);
        this.billService = new BillService(billRepository);
    }

    @Test
    public void getBillsOk(){
        when(billRepository.getBills()).thenReturn(new ArrayList<BillsWithoutPhoneCalls>());

        Assert.assertEquals(new ArrayList<BillsWithoutPhoneCalls>(), billService.getBills());
    }

    @Test
    public void getBillsByDateRangeOk(){
        when(billRepository.getBillsByDateRange(1,
                new GregorianCalendar(2020, Calendar.MARCH, 1).getTime(), Date.from(Instant.now())))
                .thenReturn(new ArrayList<BillsForUsers>());

        Assert.assertEquals(new ArrayList<BillsForUsers>(),
                billService.getBillsByDateRange(1,
                new GregorianCalendar(2020, Calendar.MARCH, 1).getTime()
                        , Date.from(Instant.now())));
    }

    @Test
    public void getBillsByDateRangeDate2Null(){
        when(billRepository.getBillsByDateRange(1,
                new GregorianCalendar(2020, Calendar.MARCH, 1).getTime(), null))
                .thenReturn(new ArrayList<BillsForUsers>());;

        Assert.assertEquals(new ArrayList<BillsForUsers>(),
                billService.getBillsByDateRange(1,
                        new GregorianCalendar(2020, Calendar.MARCH, 1).getTime(), null));
    }

    @Test(expected = NotFoundException.class)
    public void getBillByIdNotFoundException(){
        when(billRepository.findById(1)).thenReturn(Optional.empty());

        billService.getBillById(1);
    }

    @Test
    public void getBillByIdOk(){
        when(billRepository.findById(1)).thenReturn(Optional.of(new Bill()));

        billService.getBillById(1);
    }

    @Test(expected = NotFoundException.class)
    public void payBillNotFoundException(){
        when(billRepository.findById(1)).thenReturn(Optional.empty());

        billService.payBill(1);
    }

    @Test(expected = NotFoundException.class)
    public void payBillPayedNotFoundException(){
        when(billRepository.findById(1)).thenReturn(Optional.of(createBillPayed()));

        billService.payBill(1);
    }

    @Test
    public void payBillOk(){
        Bill bill = createBill();
        when(billRepository.findById(1)).thenReturn(Optional.of(bill));
        when(billRepository.save(bill)).thenReturn(bill);

        Assert.assertEquals(bill, billService.payBill(1));
    }
}
