package edu.utn.utnPhones.controller;

import edu.utn.utnPhones.model.PhoneCall;
import edu.utn.utnPhones.projections.CallsByDateRange;
import edu.utn.utnPhones.projections.MostCalledDestination;
import edu.utn.utnPhones.service.PhoneCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
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

    @GetMapping("/{idUser}/")
    public List<CallsByDateRange> getCallsByDateRange(@PathVariable(value = "idUser") Integer idUser,
                                                      @RequestParam(value = "date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
                                                      @RequestParam(value = "date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2){
        return phoneCallService.getCallsByDateRange(idUser, date1, date2);
    }

    @PostMapping("/")
    public void add(@RequestBody @Valid PhoneCall phoneCall){
        phoneCallService.add(phoneCall);
    }

    @GetMapping("/{idUser}/destinations")
    public List<MostCalledDestination> getMostCalledDestinations(@PathVariable(value = "idUser") Integer idUser){
        return phoneCallService.getMostCalledDestinations(idUser);
    }
}
