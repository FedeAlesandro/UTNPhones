package edu.utn.utnPhones.service;

import edu.utn.utnPhones.dao.TariffRepository;
import edu.utn.utnPhones.model.Tariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TariffService {

    private TariffRepository tariffRepository;

    @Autowired
    public TariffService(TariffRepository tariffRepository){
        this.tariffRepository = tariffRepository;
    }

    public List<Tariff> getAll() {
        return tariffRepository.findAll();
    }

    public void add(Tariff tariff) {
        tariffRepository.save(tariff);
    }
}
