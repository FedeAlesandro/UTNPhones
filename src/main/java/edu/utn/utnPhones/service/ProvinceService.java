package edu.utn.utnPhones.service;

import edu.utn.utnPhones.dao.ProvinceRepository;
import edu.utn.utnPhones.model.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService {

    private ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public List<Province> getAll(){
        return provinceRepository.findAll();
    }

    public void add(Province province){
        provinceRepository.save(province);
    }
}
