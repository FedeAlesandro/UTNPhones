package edu.utn.utnPhones.service;

import edu.utn.utnPhones.dao.PhoneCallRepository;
import edu.utn.utnPhones.model.PhoneCall;
import edu.utn.utnPhones.projections.CallsByDateRange;
import edu.utn.utnPhones.projections.MostCalledDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PhoneCallService {

    private PhoneCallRepository phoneCallRepository;

    @Autowired
    public PhoneCallService(PhoneCallRepository phoneCallRepository){
        this.phoneCallRepository = phoneCallRepository;
    }

    public List<PhoneCall> getAll() {
        return phoneCallRepository.findAll();
    }

    public void add(PhoneCall phoneCall) {
        phoneCallRepository.save(phoneCall);
    }

    public List<CallsByDateRange> getCallsByDateRange(Integer idUser, LocalDate date1, LocalDate date2) {
        return phoneCallRepository.getCallsByDateRange(idUser, date1, date2);
    }

    public List<MostCalledDestination> getMostCalledDestinations(Integer idUser){
        return phoneCallRepository.getMostCalledDestinations(idUser);
    }
}
