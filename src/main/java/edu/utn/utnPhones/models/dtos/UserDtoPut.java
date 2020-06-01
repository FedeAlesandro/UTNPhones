package edu.utn.utnPhones.models.dtos;

import edu.utn.utnPhones.models.City;
import edu.utn.utnPhones.models.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoPut {

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    @NotBlank
    private String dni;

    @NotBlank
    private String userName;

    @NotBlank
    private String pwd;

    @NotNull
    private String city;

    @NotNull
    private String areaCode;

    @NotNull
    private String province;

}
