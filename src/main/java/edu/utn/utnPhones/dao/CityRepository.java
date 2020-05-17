package edu.utn.utnPhones.dao;

import edu.utn.utnPhones.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
}
