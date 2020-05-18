package edu.utn.utnPhones.controller;

import edu.utn.utnPhones.model.PhoneLine;
import edu.utn.utnPhones.service.PhoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/phoneLines")
public class PhoneLineController {

    private PhoneLineService phoneLineService;

    @Autowired
    public PhoneLineController(PhoneLineService phoneLineService) {
        this.phoneLineService = phoneLineService;
    }

    @GetMapping("/")
    public List<PhoneLine> getAll(){
        return phoneLineService.getAll();
    }

    @PostMapping("/")
    public void add(@RequestBody @Valid PhoneLine phoneLine){
        phoneLineService.add(phoneLine);
    }
}
