package edu.utn.utnPhones.repositories;

import edu.utn.utnPhones.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    @Query(value = "select * " +
            "from cities c " +
            "join provinces p " +
            "on c.id_province = p.id_province " +
            "where c.city_name = ?1 and c.area_code = ?2 and p.province_name = ?3 ;", nativeQuery = true)
    Optional<City> findByNameAndAreaCodeAndProvince(String name, String areaCode, String provinceName);

    @Query(value = "select * from cities as c where ?1 like CONCAT(c.area_code,'%') ;", nativeQuery = true)
    Optional<City> findAreaCodeByPhoneNumber(String phoneNumber);
}
