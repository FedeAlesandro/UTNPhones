package edu.utn.utnPhones.services;

import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.models.Tariff;
import edu.utn.utnPhones.repositories.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static edu.utn.utnPhones.utils.Constants.NOT_FOUND_TARIFF;

@Service
@RequiredArgsConstructor
public class TariffService {

    private final TariffRepository tariffRepository;

    public List<Tariff> getAll() {

        return tariffRepository.findAll();
    }

    public Tariff getById(Integer id){

        return tariffRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_TARIFF));
    }
}
