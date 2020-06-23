package edu.utn.utnPhones.repositories;

import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.enums.UserType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void findByUserTypeAndRemovedOk(){
        Assert.assertNotNull(userRepository.findByUserTypeAndRemoved(UserType.client, false));
    }

    @Test
    public void findByIdAndRemovedOk(){
        Assert.assertNotNull(userRepository.findByIdAndRemoved(1, false));
    }

    @Test
    public void findByUserNameAndRemovedOk(){
        Assert.assertNotNull(userRepository.findByUserNameAndRemoved("Euvenias", false));
    }

    @Test
    public void findByIdAndUserNameAndRemovedOk(){
        Assert.assertNotNull(userRepository.findByIdAndUserNameAndRemoved("Euvenias", false, 1));
    }

    @Test
    public void getIdByUserNameOk(){
        userRepository.save(User.builder()
                        .userName("Euvenias")
                        .removed(false)
                        .build());
        Assert.assertNotNull(userRepository.getIdByUserName("Euvenias", false));
    }

    @Test
    public void findByUserNameAndRemovedAndUserTypeOk(){
        Assert.assertNotNull(userRepository.findByUserNameAndRemovedAndUserType("Euvenias", false, UserType.client));
    }

    @Test
    public void findByDniOk(){
        userRepository.save(User.builder()
                .dni("42454677")
                .removed(false)
                .build());
        Assert.assertNotNull(userRepository.findByDni("42454677"));
    }

    @Test
    public void findByDniAndId(){
        Assert.assertNotNull(userRepository.findByDniAndId("42454677", 1));
    }
}
