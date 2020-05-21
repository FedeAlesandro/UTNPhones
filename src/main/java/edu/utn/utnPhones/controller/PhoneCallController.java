package edu.utn.utnPhones.controller;

import edu.utn.utnPhones.model.PhoneCall;
import edu.utn.utnPhones.projections.MostCalledDestination;
import edu.utn.utnPhones.service.PhoneCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/calls")
public class PhoneCallController {

    private PhoneCallService phoneCallService;

    @Autowired
    public PhoneCallController(PhoneCallService phoneCallService){
        this.phoneCallService = phoneCallService;
    }

    @GetMapping("/")
    public List<PhoneCall> getAll(){
        return phoneCallService.getAll();
    }

    @PostMapping("/")
    public void add(@RequestBody @Valid PhoneCall phoneCall){
        phoneCallService.add(phoneCall);
    }

    @GetMapping("/destinations")
    public List<MostCalledDestination> getMostCalledDestinations(@RequestParam Integer id_user){
        return phoneCallService.getMostCalledDestinations(id_user);
    }
}
