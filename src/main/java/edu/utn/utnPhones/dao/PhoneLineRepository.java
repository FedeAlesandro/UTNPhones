package edu.utn.utnPhones.dao;

import edu.utn.utnPhones.model.PhoneLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PhoneLineRepository extends JpaRepository<PhoneLine, Integer> {

    @Modifying
    @Transactional
    @Query(value = "update phone_lines set state='register' where id_phone_line = ?1 ;", nativeQuery = true)
    void register(Integer id);

    @Modifying
    @Transactional
    @Query(value = "update phone_lines set state='suspended' where id_phone_line = ?1 ;", nativeQuery = true)
    public void suspend(Integer id);

    @Modifying
    @Transactional
    @Query(value = "update phone_lines set state='removed' where id_phone_line = ?1 ;", nativeQuery = true)
    public void remove(Integer id);
}
