package edu.utn.utnPhones.dao;

import edu.utn.utnPhones.model.PhoneCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneCallRepository extends JpaRepository<PhoneCall,Integer> {
}
