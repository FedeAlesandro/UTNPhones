package edu.utn.utnPhones.models.dtos;

import edu.utn.utnPhones.models.PhoneCall;
import lombok.Data;

import java.util.Date;

@Data
public class PhoneCallPhoneLineDto {

    private Integer id;

    private Date date;

    private String originPhoneNumber;

    private String destinationPhoneNumber;

    public PhoneCallPhoneLineDto(PhoneCall phoneCall){
        id = phoneCall.getId();
        date = phoneCall.getDate();
        originPhoneNumber = phoneCall.getOriginPhoneNumber();
        destinationPhoneNumber = phoneCall.getDestinationPhoneNumber();
    }

}
