package edu.utn.utnPhones.services;

import edu.utn.utnPhones.repositories.PhoneCallRepository;
import edu.utn.utnPhones.models.PhoneCall;
import edu.utn.utnPhones.models.projections.CallsByDateRange;
import edu.utn.utnPhones.models.projections.CallsByUser;
import edu.utn.utnPhones.models.projections.MostCalledDestination;
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

    public List<CallsByUser> getByUser(Integer idUser){
        return phoneCallRepository.getByUser(idUser);
    }
}
