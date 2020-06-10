package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.PhoneCall;
import edu.utn.utnPhones.models.dtos.PhoneCallDtoAdd;
import edu.utn.utnPhones.models.dtos.PhoneCallDtoResponse;
import edu.utn.utnPhones.models.projections.CallsByDateRange;
import edu.utn.utnPhones.models.projections.CallsByUser;
import edu.utn.utnPhones.models.projections.MostCalledDestination;
import edu.utn.utnPhones.services.PhoneCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;

@Controller
public class PhoneCallController {

    private final PhoneCallService phoneCallService;

    @Autowired
    public PhoneCallController(PhoneCallService phoneCallService){
        this.phoneCallService = phoneCallService;
    }

    public List<CallsByDateRange> getCallsByDateRange(Integer idUser, Date date1, Date date2){

        return phoneCallService.getCallsByDateRange(idUser, date1, date2);
    }

    public List<MostCalledDestination> getMostCalledDestinations(Integer idUser) {

        return phoneCallService.getMostCalledDestinations(idUser);
    }

    public List<CallsByUser> getByUser(Integer idUser){

        return phoneCallService.getByUser(idUser);
    }

    public PhoneCall add(PhoneCallDtoAdd phoneCall){

        return phoneCallService.add(phoneCall);
    }

}
