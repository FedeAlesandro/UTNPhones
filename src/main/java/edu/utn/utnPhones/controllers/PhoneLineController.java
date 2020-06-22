package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.PhoneLine;
import edu.utn.utnPhones.models.dtos.requests.PhoneLineDtoAdd;
import edu.utn.utnPhones.models.dtos.requests.PhoneLineDtoUpdate;
import edu.utn.utnPhones.services.PhoneLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PhoneLineController {

    private final PhoneLineService phoneLineService;

    public List<PhoneLine> getAll(){

        return phoneLineService.getAll();
    }

    public List<PhoneLine> getByUserName(String userName){

        return phoneLineService.getByUserName(userName);
    }

    public PhoneLine add(PhoneLineDtoAdd phoneLine){

        return phoneLineService.add(phoneLine);
    }

    public PhoneLine update(Integer id, PhoneLineDtoUpdate phoneLineUpdate){

        return phoneLineService.update(id, phoneLineUpdate);
    }

    public void remove(Integer id){

        phoneLineService.remove(id);
    }
}
