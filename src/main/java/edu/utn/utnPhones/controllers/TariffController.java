package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.Tariff;
import edu.utn.utnPhones.services.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TariffController {

    private final TariffService tariffService;

    @Autowired
    public TariffController(TariffService tariffService){
        this.tariffService = tariffService;
    }

    public List<Tariff> getAll(){
        return tariffService.getAll();
    }

    public Tariff getById(Integer id){
        return tariffService.getById(id);
    }
}
