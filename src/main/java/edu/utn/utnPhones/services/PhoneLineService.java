package edu.utn.utnPhones.services;

import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.exceptions.PhoneLineRemovedException;
import edu.utn.utnPhones.models.enums.PhoneLineStatus;
import edu.utn.utnPhones.models.dtos.requests.PhoneLineDtoAdd;
import edu.utn.utnPhones.models.dtos.requests.PhoneLineDtoUpdate;
import edu.utn.utnPhones.repositories.PhoneLineRepository;
import edu.utn.utnPhones.models.PhoneLine;
import edu.utn.utnPhones.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static edu.utn.utnPhones.utils.Constants.NOT_FOUND_PHONE_LINE;
import static edu.utn.utnPhones.utils.Constants.PHONE_LINE_NOT_REMOVED;
import static edu.utn.utnPhones.utils.Constants.PHONE_LINE_REMOVED;
import static edu.utn.utnPhones.utils.Constants.USER_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class PhoneLineService {

    private final PhoneLineRepository phoneLineRepository;

    private final UserRepository userRepository;

    public List<PhoneLine> getAll(){

       return phoneLineRepository.getAll();
    }

    public List<PhoneLine> getByUserName(String userName) {

        List<PhoneLine> phoneLines = phoneLineRepository.findByUserName(userName, false);

        return phoneLines.stream()
                .filter(phoneLine -> !phoneLine.getState().equals(PhoneLineStatus.removed))
                .collect(Collectors.toList());
    }

    public PhoneLine add (PhoneLineDtoAdd phoneLineAdd){

        userRepository.findById(phoneLineAdd.getUser().getId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_EXIST));

        PhoneLine oldPhoneLine = phoneLineRepository.findByPhoneNumber(phoneLineAdd.getPhoneNumber());
        PhoneLine phoneLine = PhoneLine.fromPhoneLineAdd(phoneLineAdd);

        if (oldPhoneLine != null){

            if(oldPhoneLine.getState().equals(PhoneLineStatus.removed)){

                oldPhoneLine.setUser(phoneLineAdd.getUser());
                oldPhoneLine.setState(PhoneLineStatus.register);

                return phoneLineRepository.save(oldPhoneLine);
            }else
                throw new PhoneLineRemovedException(PHONE_LINE_NOT_REMOVED);
        }

        return phoneLineRepository.save(phoneLine);
    }

    public PhoneLine update (Integer id, PhoneLineDtoUpdate phoneLineUpdate){

        PhoneLine phoneLine = phoneLineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_PHONE_LINE));

        if (phoneLine.getState().equals(PhoneLineStatus.removed))
            throw new PhoneLineRemovedException(PHONE_LINE_REMOVED);

        userRepository.findById(phoneLineUpdate.getUser().getId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_EXIST));

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
