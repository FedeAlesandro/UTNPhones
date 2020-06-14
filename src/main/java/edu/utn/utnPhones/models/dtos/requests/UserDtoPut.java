package edu.utn.utnPhones.models.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoPut {

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "[a-zA-Z]+", message = "Name should contain only letters")
    @Size(min = 2, max = 40, message = "Invalid size for name")
    private String name;

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "[a-zA-Z]+", message = "Last name should contain only letters")
    @Size(min = 2, max = 40, message = "Invalid size for last name")
    private String lastName;

    @NotBlank(message = "DNI is required")
    @Pattern(regexp = "[0-9]+", message = "DNI should be made up of numbers")
    @Size(min = 7, max = 8, message = "Invalid size for DNI")
    private String dni;

    @NotBlank(message = "Username is required")
    @Pattern(regexp = "[0-9a-zA-Z\\.\\-\\_]+", message = "Invalid username, choose another one")
    @Size(min = 8, max = 40, message = "Invalid size for username")
    private String userName;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "[0-9a-zA-Z]+", message = "Password should contain numbers, upper and lower case letters only")
    @Size(min = 8, max = 40, message = "Invalid size for password")
    private String pwd;

    @NotBlank(message = "City is required")
    @Pattern(regexp = "[0-9a-zA-Z]+", message = "Cities should contain only letters or numbers")
    @Size(min = 4, max = 60, message = "Invalid size for city")
    private String city;

    @NotBlank(message = "Area code is required")
    @Pattern(regexp = "[0-9]+", message = "The area code should contain only numbers")
    @Size(min = 2, max = 4, message = "Invalid size for area code")
    private String areaCode;

    @NotBlank(message = "Province is required")
    @Pattern(regexp = "[a-zA-Z]+", message = "Provinces should contain only letters")
    @Size(min = 4, max = 40, message = "Invalid size for province")
    private String province;
}
