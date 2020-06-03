package edu.utn.utnPhones.models.dtos;

import edu.utn.utnPhones.models.PhoneCall;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PhoneCallPhoneLineDto {

    private Integer id;

    private LocalDate date;

    private String originPhoneNumber;

    private String destinationPhoneNumber;

    public PhoneCallPhoneLineDto(PhoneCall phoneCall){
        id = phoneCall.getId();
        date = phoneCall.getDate();
        originPhoneNumber = phoneCall.getOriginPhoneNumber();
        destinationPhoneNumber = phoneCall.getDestinationPhoneNumber();
    }

}
