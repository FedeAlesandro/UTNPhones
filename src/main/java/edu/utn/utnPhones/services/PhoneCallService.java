package edu.utn.utnPhones.services;

import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.models.PhoneCall;
import edu.utn.utnPhones.models.dtos.requests.PhoneCallDtoAdd;
import edu.utn.utnPhones.models.projections.CallsByDateRange;
import edu.utn.utnPhones.models.projections.CallsByUser;
import edu.utn.utnPhones.models.projections.MostCalledDestination;
import edu.utn.utnPhones.repositories.PhoneCallRepository;
import edu.utn.utnPhones.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

import static edu.utn.utnPhones.utils.Constants.USER_NOT_EXIST_ID;

@Service
@RequiredArgsConstructor
public class PhoneCallService {

    private final PhoneCallRepository phoneCallRepository;

    private final UserRepository userRepository;

    public URI add(PhoneCallDtoAdd phoneCall) {

        PhoneCall phoneCallToAdd = PhoneCall.fromDto(phoneCall);

        phoneCallToAdd = phoneCallRepository.save(phoneCallToAdd);

        phoneCallToAdd.setDate(phoneCallRepository.getDateById(phoneCallToAdd.getId()));

        return getLocation(phoneCallToAdd);
    }

    private URI getLocation(PhoneCall phoneCall) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idPhoneCall}")
                .buildAndExpand(phoneCall.getId())
                .toUri();
    }

    public List<CallsByDateRange> getCallsByDateRange(Integer idUser, Date date1, Date date2) {

        if (!userRepository.existsById(idUser)){
            throw new NotFoundException(USER_NOT_EXIST_ID);
        }

        return phoneCallRepository.getCallsByDateRange(idUser, date1, date2);
    }

    public List<MostCalledDestination> getMostCalledDestinations(Integer idUser){

        if (!userRepository.existsById(idUser)){
            throw new NotFoundException(USER_NOT_EXIST_ID);
        }

        return phoneCallRepository.getMostCalledDestinations(idUser);
    }

    public List<CallsByUser> getByUser(Integer idUser){

        if (!userRepository.existsById(idUser)){
            throw new NotFoundException(USER_NOT_EXIST_ID);
        }

        return phoneCallRepository.getByUser(idUser);
    }
}
