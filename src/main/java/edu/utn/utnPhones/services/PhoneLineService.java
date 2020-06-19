package edu.utn.utnPhones.services;

import edu.utn.utnPhones.exceptions.AlreadyExistsException;
import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.models.PhoneLine;
import edu.utn.utnPhones.models.dtos.requests.PhoneLineDtoAdd;
import edu.utn.utnPhones.models.dtos.requests.PhoneLineDtoUpdate;
import edu.utn.utnPhones.models.enums.PhoneLineStatus;
import edu.utn.utnPhones.repositories.PhoneLineRepository;
import edu.utn.utnPhones.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static edu.utn.utnPhones.utils.Constants.ALREADY_EXISTS_PHONE_LINE;
import static edu.utn.utnPhones.utils.Constants.NOT_FOUND_PHONE_LINE;
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

        userRepository.findByUserNameAndRemoved(userName, false)
                .orElseThrow(() -> new NotFoundException(USER_NOT_EXIST));

        List<PhoneLine> phoneLines = phoneLineRepository.getAll();

        return phoneLines.stream()
                .filter(phoneLine -> !phoneLine.getState().equals(PhoneLineStatus.removed))
                .collect(Collectors.toList());
    }

    public URI add (PhoneLineDtoAdd phoneLineAdd){

        userRepository.findByIdAndRemoved(phoneLineAdd.getUser().getId(), false)
                .orElseThrow(() -> new NotFoundException(USER_NOT_EXIST));

        PhoneLine oldPhoneLine = phoneLineRepository.findByPhoneNumber(phoneLineAdd.getPhoneNumber());
        PhoneLine phoneLine = PhoneLine.fromPhoneLineAdd(phoneLineAdd);

        if (oldPhoneLine != null){

            if(oldPhoneLine.getState().equals(PhoneLineStatus.removed)){

                oldPhoneLine.setUser(phoneLineAdd.getUser());
                oldPhoneLine.setState(PhoneLineStatus.register);

                return getLocation(phoneLineRepository.save(oldPhoneLine));
            }else
                throw new AlreadyExistsException(ALREADY_EXISTS_PHONE_LINE);
        }

        return getLocation(phoneLineRepository.save(phoneLine));
    }

    private URI getLocation(PhoneLine phoneLine) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idPhoneLine}")
                .buildAndExpand(phoneLine.getId())
                .toUri();
    }

    public PhoneLine update (Integer id, PhoneLineDtoUpdate phoneLineUpdate){

        PhoneLine phoneLine = phoneLineRepository.findByIdAndState(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_PHONE_LINE));

        if(phoneLineRepository.findByPhoneNumber(phoneLineUpdate.getPhoneNumber()) != null)
            throw new AlreadyExistsException(ALREADY_EXISTS_PHONE_LINE);

        userRepository.findByIdAndRemoved(phoneLineUpdate.getUser().getId(), false)
                .orElseThrow(() -> new NotFoundException(USER_NOT_EXIST));

        phoneLine.setUser(phoneLineUpdate.getUser());
        phoneLine.setPhoneNumber(phoneLineUpdate.getPhoneNumber());
        phoneLine.setLineType(phoneLineUpdate.getLineType());
        phoneLine.setState(phoneLineUpdate.getState());

        return phoneLineRepository.save(phoneLine);
    }

    public void remove (Integer id){

        phoneLineRepository.findByIdAndState(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_PHONE_LINE));

        phoneLineRepository.remove(id);
    }
}
