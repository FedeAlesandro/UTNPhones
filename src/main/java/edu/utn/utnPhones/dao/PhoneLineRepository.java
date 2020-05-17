package edu.utn.utnPhones.dao;

import edu.utn.utnPhones.model.PhoneLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneLineRepository extends JpaRepository<PhoneLine, Integer> {
}
