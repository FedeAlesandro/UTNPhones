package edu.utn.utnPhones.repositories;

import edu.utn.utnPhones.models.PhoneLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhoneLineRepository extends JpaRepository<PhoneLine, Integer> {

    @Modifying
    @Transactional
    @Query(value = "update phone_lines set state='removed' where id_phone_line = ?1 ;", nativeQuery = true)
    void remove(Integer id);

    @Query (value = "select * from phone_lines where state <> 'removed' ;", nativeQuery = true)
    List<PhoneLine> getAll();

    PhoneLine findByPhoneNumber(String phoneNumber);
}
