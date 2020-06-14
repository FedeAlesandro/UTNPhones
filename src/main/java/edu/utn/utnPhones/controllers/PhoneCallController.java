package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.PhoneCall;
import edu.utn.utnPhones.models.dtos.requests.PhoneCallDtoAdd;
import edu.utn.utnPhones.models.projections.CallsByDateRange;
import edu.utn.utnPhones.models.projections.CallsByUser;
import edu.utn.utnPhones.models.projections.MostCalledDestination;
import edu.utn.utnPhones.services.PhoneCallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PhoneCallController {

    private final PhoneCallService phoneCallService;

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
