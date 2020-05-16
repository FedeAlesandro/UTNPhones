package edu.utn.utnPhones.dao;

import edu.utn.utnPhones.model.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepository extends JpaRepository<Tariff,Integer> {
}
