package edu.utn.utnPhones.controller;

import edu.utn.utnPhones.model.PhoneCall;
import edu.utn.utnPhones.service.PhoneCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping
    public List<PhoneCall> getPhoneCalls(){
        return phoneCallService.getPhoneCalls();
    }

    @PostMapping
    public void addPhoneCall(@RequestBody @Valid PhoneCall phoneCall){
        phoneCallService.addPhoneCall(phoneCall);
    }
}
