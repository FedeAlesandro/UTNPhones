package edu.utn.utnPhones.models.dtos;

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
public class UserDtoPatch {

    private String name;

    private String lastName;

    private String dni;

    private String userName;

    private String pwd;

    private String city;

    private String areaCode;

    private String province;
}
