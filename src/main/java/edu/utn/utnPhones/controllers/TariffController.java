package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.Tariff;
import edu.utn.utnPhones.services.TariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TariffController {

    private final TariffService tariffService;

    public List<Tariff> getAll(){

        return tariffService.getAll();
    }

    public Tariff getById(Integer id){

        return tariffService.getById(id);
    }
}
