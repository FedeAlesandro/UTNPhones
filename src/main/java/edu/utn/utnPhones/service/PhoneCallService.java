package edu.utn.utnPhones.service;

import edu.utn.utnPhones.dao.PhoneCallRepository;
import edu.utn.utnPhones.model.PhoneCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
