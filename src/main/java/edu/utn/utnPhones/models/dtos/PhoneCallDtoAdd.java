package edu.utn.utnPhones.models.dtos;

import lombok.Data;

@Data
public class PhoneCallDtoAdd {

    private Integer duration;

    private String originPhoneNumber;

    private String destinationPhoneNumber;

}
