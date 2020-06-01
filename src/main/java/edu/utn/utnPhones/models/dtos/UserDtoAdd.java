package edu.utn.utnPhones.models.dtos;

import edu.utn.utnPhones.models.City;
import edu.utn.utnPhones.models.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoAdd {

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
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @NotNull
    private String city;

    private String areaCode;

    private String province;
}
