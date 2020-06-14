package edu.utn.utnPhones.models.dtos.requests;

import edu.utn.utnPhones.models.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoAdd {

    @NotBlank(message = "Name should not be empty")
    @Pattern(regexp = "[a-zA-Z]+", message = "Name should contain only letters")
    @Size(min = 2, max = 40, message = "Invalid size for name")
    private String name;

    @NotBlank(message = "Last name should not be empty")
    @Pattern(regexp = "[a-zA-Z]+", message = "Last name should contain only letters")
    @Size(min = 2, max = 40, message = "Invalid size for last name")
    private String lastName;

    @NotBlank(message = "DNI should not be empty")
    @Pattern(regexp = "[0-9]+", message = "DNI should be made up of numbers")
    @Size(min = 7, max = 8, message = "Invalid size for DNI")
    private String dni;

    @NotBlank(message = "Username should not be empty")
    @Pattern(regexp = "[0-9a-zA-Z\\.\\-\\_]+", message = "Invalid username, choose another one")
    @Size(min = 8, max = 40, message = "Invalid size for username")
    private String userName;

    @NotBlank(message = "Password should not be empty")
    @Pattern(regexp = "[0-9a-zA-Z]+", message = "Password should contain numbers, upper and lower case letters only")
    @Size(min = 8, max = 40, message = "Invalid size for password")
    private String pwd;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserType userType;

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
