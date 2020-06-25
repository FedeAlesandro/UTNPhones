package edu.utn.utnPhones.services;

import edu.utn.utnPhones.exceptions.AlreadyExistsException;
import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.exceptions.UnauthorizedUserTypeException;
import edu.utn.utnPhones.models.PhoneLine;
import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.dtos.requests.PhoneLineDtoAdd;
import edu.utn.utnPhones.models.dtos.requests.PhoneLineDtoUpdate;
import edu.utn.utnPhones.models.enums.PhoneLineStatus;
import edu.utn.utnPhones.models.enums.UserType;
import edu.utn.utnPhones.repositories.CityRepository;
import edu.utn.utnPhones.repositories.PhoneLineRepository;
import edu.utn.utnPhones.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static edu.utn.utnPhones.utils.Constants.ALREADY_EXISTS_PHONE_LINE;
import static edu.utn.utnPhones.utils.Constants.NOT_FOUND_AREA_CODE;
import static edu.utn.utnPhones.utils.Constants.NOT_FOUND_PHONE_LINE;
import static edu.utn.utnPhones.utils.Constants.UNAUTHORIZED_USER_HANDLING;
import static edu.utn.utnPhones.utils.Constants.USER_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class PhoneLineService {

    private final PhoneLineRepository phoneLineRepository;

    private final UserRepository userRepository;

    private final CityRepository cityRepository;

    public List<PhoneLine> getAll(){

       return phoneLineRepository.getAll();
    }

    public List<PhoneLine> getByUserName(String userName) {

        User user = userRepository.findByUserNameAndRemoved(userName, false)
                .orElseThrow(() -> new NotFoundException(USER_NOT_EXIST));

        if(user.getUserType() == UserType.infrastructure)
            throw new UnauthorizedUserTypeException(UNAUTHORIZED_USER_HANDLING);

        List<PhoneLine> phoneLines = phoneLineRepository.findByUserName(userName, false);

        return phoneLines.stream()
                .filter(phoneLine -> !phoneLine.getState().equals(PhoneLineStatus.removed))
                .collect(Collectors.toList());
    }

    public PhoneLine add (PhoneLineDtoAdd phoneLineAdd){

        User user = userRepository.findByIdAndRemoved(phoneLineAdd.getUser().getId(), false)
                .orElseThrow(() -> new NotFoundException(USER_NOT_EXIST));

        if(user.getUserType() == UserType.infrastructure)
            throw new UnauthorizedUserTypeException(UNAUTHORIZED_USER_HANDLING);

        cityRepository.findAreaCodeByPhoneNumber(phoneLineAdd.getPhoneNumber())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_AREA_CODE));

        PhoneLine oldPhoneLine = phoneLineRepository.findByPhoneNumber(phoneLineAdd.getPhoneNumber());
        PhoneLine phoneLine = PhoneLine.fromPhoneLineAdd(phoneLineAdd);

        if (oldPhoneLine != null){

            if(oldPhoneLine.getState().equals(PhoneLineStatus.removed)){

                oldPhoneLine.setUser(phoneLineAdd.getUser());
                oldPhoneLine.setState(PhoneLineStatus.register);

                return phoneLineRepository.save(oldPhoneLine);
            }else
                throw new AlreadyExistsException(ALREADY_EXISTS_PHONE_LINE);
        }

        return phoneLineRepository.save(phoneLine);
    }

    public PhoneLine update (Integer id, PhoneLineDtoUpdate phoneLineUpdate){

        PhoneLine phoneLine = phoneLineRepository.findByIdAndState(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_PHONE_LINE));

        cityRepository.findAreaCodeByPhoneNumber(phoneLineUpdate.getPhoneNumber())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_AREA_CODE));

        PhoneLine oldPhoneLine = phoneLineRepository.findByPhoneNumber(phoneLineUpdate.getPhoneNumber());

        if(oldPhoneLine != null)
            if(!oldPhoneLine.getPhoneNumber().equals(phoneLineUpdate.getPhoneNumber()))
                throw new AlreadyExistsException(ALREADY_EXISTS_PHONE_LINE);

        User user = userRepository.findByIdAndRemoved(phoneLineUpdate.getUser().getId(), false)
                .orElseThrow(() -> new NotFoundException(USER_NOT_EXIST));

        phoneLine.setUser(user);
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
