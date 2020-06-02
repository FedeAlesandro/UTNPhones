package edu.utn.utnPhones.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoPut {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "DNI is required")
    private String dni;

    @NotBlank(message = "Username is required")
    private String userName;

    @NotBlank(message = "A password is required")
    private String pwd;

    @NotBlank(message = "City name is required")
    private String city;

    @NotBlank(message = "A area code is required")
    private String areaCode;

    @NotBlank(message = "Province name is required")
    private String province;

}
