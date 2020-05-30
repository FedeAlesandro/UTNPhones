package edu.utn.utnPhones.repositories;

import edu.utn.utnPhones.models.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepository extends JpaRepository<Tariff,Integer> {
}
