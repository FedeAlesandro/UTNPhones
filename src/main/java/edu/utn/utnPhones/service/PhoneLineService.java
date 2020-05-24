package edu.utn.utnPhones.service;

import edu.utn.utnPhones.dao.PhoneLineRepository;
import edu.utn.utnPhones.model.PhoneLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneLineService {

    private PhoneLineRepository phoneLineRepository;

    @Autowired
    public PhoneLineService(PhoneLineRepository phoneLineRepository) {
        this.phoneLineRepository = phoneLineRepository;
    }

    public List<PhoneLine> getAll(){
       return phoneLineRepository.findAll();
    }

    public void add (PhoneLine phoneLine){
        phoneLineRepository.save(phoneLine);
    }

    public void register(Integer id) {
        phoneLineRepository.register(id);
    }

    public void suspend (Integer id){
        phoneLineRepository.suspend(id);
    }

    public void remove (Integer id){
        phoneLineRepository.remove(id);
    }
}
