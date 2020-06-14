package edu.utn.utnPhones.models.dtos.requests;

import edu.utn.utnPhones.models.enums.LineType;
import edu.utn.utnPhones.models.enums.PhoneLineStatus;
import edu.utn.utnPhones.models.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PhoneLineDtoUpdate {

    @NotNull
    private User user;

    @NotBlank
    @Size(min = 9, max = 12)
    private String phoneNumber;

    @NotNull
    private LineType lineType;

    @NotNull
    private PhoneLineStatus state;
}
