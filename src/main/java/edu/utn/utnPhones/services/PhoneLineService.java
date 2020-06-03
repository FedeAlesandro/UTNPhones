package edu.utn.utnPhones.services;

import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.exceptions.PhoneLineRemovedException;
import edu.utn.utnPhones.models.PhoneLineStatus;
import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.UserType;
import edu.utn.utnPhones.models.dtos.PhoneLineAdd;
import edu.utn.utnPhones.models.dtos.PhoneLineUpdate;
import edu.utn.utnPhones.repositories.PhoneLineRepository;
import edu.utn.utnPhones.models.PhoneLine;
import edu.utn.utnPhones.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static edu.utn.utnPhones.utils.Constants.NOT_FOUND_PHONE_LINE;
import static edu.utn.utnPhones.utils.Constants.PHONE_LINE_NOT_REMOVED;
import static edu.utn.utnPhones.utils.Constants.PHONE_LINE_REMOVED;

@Service
public class PhoneLineService {

    private final PhoneLineRepository phoneLineRepository;
    private final UserRepository userRepository;

    @Autowired
    public PhoneLineService(PhoneLineRepository phoneLineRepository, UserRepository userRepository) {
        this.phoneLineRepository = phoneLineRepository;
        this.userRepository = userRepository;
    }

    public List<PhoneLine> getAll(){
       return phoneLineRepository.getAll();
    }

    public List<PhoneLine> getAllWithoutFilter(){
        return phoneLineRepository.findAll();
    }

    public PhoneLine getById(Integer id) {
        PhoneLine phoneLine = phoneLineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_PHONE_LINE));

        if(phoneLine.getState().equals(PhoneLineStatus.removed))
            throw new PhoneLineRemovedException(PHONE_LINE_REMOVED);

        return phoneLine;
    }

    public PhoneLine add (PhoneLineAdd phoneLineAdd){

        User user = userRepository.findById(phoneLineAdd.getUser().getId())
                .orElseThrow(() -> new NotFoundException("User doesn't exist"));  //todo usar constante de fabio

        if(user.getUserType().equals(UserType.employee))
            throw new RuntimeException(); //todo fijarse si fabio tiene una excepcion ya en usuario para no hacerla again

        PhoneLine oldPhoneLine = phoneLineRepository.findByPhoneNumber(phoneLineAdd.getPhoneNumber());
        PhoneLine phoneLine = PhoneLine.fromPhoneLineAdd(phoneLineAdd);

        if(oldPhoneLine != null){
            if(oldPhoneLine.getState().equals(PhoneLineStatus.removed)){
                oldPhoneLine.setUser(phoneLineAdd.getUser());
                oldPhoneLine.setState(PhoneLineStatus.register);
                return phoneLineRepository.save(oldPhoneLine);
            }else
                throw new PhoneLineRemovedException(PHONE_LINE_NOT_REMOVED);
        }

        return phoneLineRepository.save(phoneLine);
    }

    public PhoneLine update (Integer id, PhoneLineUpdate phoneLineUpdate){
        PhoneLine phoneLine = phoneLineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_PHONE_LINE));

        if(phoneLine.getState().equals(PhoneLineStatus.removed))
            throw new PhoneLineRemovedException(PHONE_LINE_REMOVED);

        userRepository.findById(phoneLineUpdate.getUser().getId())
                .orElseThrow(() -> new NotFoundException("User doesn't exist"));  //todo usar constante de fabio

        phoneLine.setUser(phoneLineUpdate.getUser());
        phoneLine.setPhoneNumber(phoneLineUpdate.getPhoneNumber());
        phoneLine.setLineType(phoneLineUpdate.getLineType());
        phoneLine.setState(phoneLineUpdate.getState());

        return phoneLineRepository.save(phoneLine);
    }

    public void remove (Integer id){
        phoneLineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_PHONE_LINE));

        phoneLineRepository.remove(id);
    }

}
