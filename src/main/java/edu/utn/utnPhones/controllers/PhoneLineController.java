package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.PhoneLine;
import edu.utn.utnPhones.services.PhoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/{id}/")
    public void register(@PathVariable(value = "id") Integer id){
        phoneLineService.register(id);
    }

    @PutMapping("/{id}/suspension/")
    public void suspend(@PathVariable(value = "id") Integer id){
        phoneLineService.suspend(id);
    }

    @DeleteMapping("/{id}/removal/")
    public void remove(@PathVariable(value = "id") Integer id){
        phoneLineService.remove(id);
    }
}
