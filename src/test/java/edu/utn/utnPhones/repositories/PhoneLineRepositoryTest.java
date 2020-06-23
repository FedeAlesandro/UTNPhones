package edu.utn.utnPhones.repositories;

import edu.utn.utnPhones.models.PhoneLine;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PhoneLineRepositoryTest {

    @Autowired
    PhoneLineRepository phoneLineRepository;

    @Test
    public void getAllOk(){
        Assert.assertNotNull(phoneLineRepository.getAll());
    }

    @Test
    public void findByPhoneNumberOk(){
        phoneLineRepository.save(PhoneLine.builder()
                .phoneNumber("2235123456")
                .build());
        Assert.assertNotNull(phoneLineRepository.findByPhoneNumber("2235123456"));
    }

    @Test
    public void findByIdAndStateOk(){
        Assert.assertNotNull(phoneLineRepository.findByIdAndState(1));
    }

    @Test
    public void removeOk(){
        phoneLineRepository.remove(1);
    }
}
