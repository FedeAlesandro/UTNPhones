package edu.utn.utnPhones.controller;

import edu.utn.utnPhones.model.Tariff;
import edu.utn.utnPhones.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tariffs")
public class TariffController {

    private TariffService tariffService;

    @Autowired
    public TariffController(TariffService tariffService){
        this.tariffService = tariffService;
    }

    @GetMapping
    public List<Tariff> getAll(){
        return tariffService.getAll();
    }

    @PostMapping
    public void add(@RequestBody @Valid Tariff tariff){
        tariffService.add(tariff);
    }
}
