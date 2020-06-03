package edu.utn.utnPhones.services;

import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.models.dtos.PhoneCallDtoAdd;
import edu.utn.utnPhones.repositories.PhoneCallRepository;
import edu.utn.utnPhones.models.PhoneCall;
import edu.utn.utnPhones.models.projections.CallsByDateRange;
import edu.utn.utnPhones.models.projections.CallsByUser;
import edu.utn.utnPhones.models.projections.MostCalledDestination;
import edu.utn.utnPhones.repositories.UserRepository;
import edu.utn.utnPhones.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PhoneCallService {

    private PhoneCallRepository phoneCallRepository;
    private UserRepository userRepository;

    @Autowired
    public PhoneCallService(PhoneCallRepository phoneCallRepository, UserRepository userRepository){
        this.phoneCallRepository = phoneCallRepository;
        this.userRepository = userRepository;
    }

    public List<PhoneCall> getAll() {
        return phoneCallRepository.findAll();
    }

    public PhoneCall add(PhoneCallDtoAdd phoneCall) {

        PhoneCall phoneCallToAdd = new PhoneCall();
        phoneCallToAdd.setDuration(phoneCall.getDuration());
        phoneCallToAdd.setOriginPhoneNumber(phoneCall.getOriginPhoneNumber());
        phoneCallToAdd.setDestinationPhoneNumber(phoneCall.getDestinationPhoneNumber());

        phoneCallToAdd = phoneCallRepository.save(phoneCallToAdd);
        phoneCallToAdd.setDate(phoneCallRepository.getDateById(phoneCallToAdd.getId()));
        return phoneCallToAdd;
    }

    public List<CallsByDateRange> getCallsByDateRange(Integer idUser, Date date1, Date date2) {

        if (!userRepository.existsById(idUser)){
            throw new NotFoundException(Constants.USER_NOT_EXIST_ID);
        }

        return phoneCallRepository.getCallsByDateRange(idUser, date1, date2);
    }

    public List<MostCalledDestination> getMostCalledDestinations(Integer idUser){

        if (!userRepository.existsById(idUser)){
            throw new NotFoundException(Constants.USER_NOT_EXIST_ID);
        }

        return phoneCallRepository.getMostCalledDestinations(idUser);
    }

    public List<CallsByUser> getByUser(Integer idUser){

        if (!userRepository.existsById(idUser)){
            throw new NotFoundException(Constants.USER_NOT_EXIST_ID);
        }

        return phoneCallRepository.getByUser(idUser);
    }

    public List<PhoneCall> getByDuration(Integer sinceDuration, Integer toDuration){
        if(sinceDuration>toDuration){
            Integer aux = sinceDuration;
            sinceDuration = toDuration;
            toDuration = aux;
        }
        return phoneCallRepository.getByDuration(sinceDuration, toDuration);
    }
}
