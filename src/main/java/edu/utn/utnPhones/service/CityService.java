package edu.utn.utnPhones.service;

import edu.utn.utnPhones.dao.CityRepository;
import edu.utn.utnPhones.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAll(){
        return cityRepository.findAll();
    }

    public void add(City city){
        cityRepository.save(city);
    }
}
