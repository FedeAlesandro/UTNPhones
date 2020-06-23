package edu.utn.utnPhones.repositories;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CityRepositoryTest {

    @Autowired
    CityRepository cityRepository;

    @Test
    public void findByNameAndAreaCodeAndProvinceOk(){
        Assert.assertNotNull(cityRepository.findByNameAndAreaCodeAndProvince("Mar del Plata", "223", "Buenos Aires"));
    }

    @Test
    public void findAreaCodeByPhoneNumberOk(){
        Assert.assertNotNull(cityRepository.findAreaCodeByPhoneNumber("2235860123"));
    }
}
