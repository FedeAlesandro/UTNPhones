package edu.utn.utnPhones.repositories;

import edu.utn.utnPhones.models.PhoneCall;
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
public class PhoneCallRepositoryTest {

    @Autowired
    PhoneCallRepository phoneCallRepository;

    @Test
    public void getCallsByDateRangeOk(){
        Assert.assertNotNull(phoneCallRepository.getCallsByDateRange(1, Date.from(Instant.now()), Date.from(Instant.now())));
    }

    @Test
    public void getMostCalledDestinationsOk(){
        Assert.assertNotNull(phoneCallRepository.getMostCalledDestinations(1));
    }

    @Test
    public void getByUserOk(){
        Assert.assertNotNull(phoneCallRepository.getByUser(1));
    }

    @Test
    public void getDateByIdOk(){
        phoneCallRepository.save(PhoneCall.builder()
                .id(1)
                .date(Date.from(Instant.now()))
                .build());
        Assert.assertNotNull(phoneCallRepository.getDateById(1));
    }
}
