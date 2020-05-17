package edu.utn.utnPhones.controller;

import edu.utn.utnPhones.model.Province;
import edu.utn.utnPhones.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/provinces")
public class ProvinceController {

    private ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping("/")
    public List<Province> getAll(){
        return provinceService.getAll();
    }

    @PostMapping("/")
    public void add(@RequestBody @Valid Province province){
        provinceService.add(province);
    }
}
