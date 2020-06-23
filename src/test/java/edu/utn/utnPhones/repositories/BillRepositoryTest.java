package edu.utn.utnPhones.repositories;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BillRepositoryTest {

    @Autowired
    BillRepository billRepository;

    @Test
    public void getBillsOk(){
        Assert.assertNotNull(billRepository.getBills());
    }

    @Test
    public void getBillsByDateRangeOk(){
        Assert.assertNotNull(billRepository.getBillsByDateRange(1, Date.from(Instant.now()), Date.from(Instant.now())));
    }
}
