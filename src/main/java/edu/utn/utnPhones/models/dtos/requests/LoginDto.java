package edu.utn.utnPhones.models.dtos.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class LoginDto {

    @NotBlank(message = "The username is required")
    @Pattern(regexp = "[0-9a-zA-Z\\.\\-\\_]+", message = "Invalid username, choose another one")
    @Size(min = 8, max = 40, message = "Invalid size for username")
    private String username;

    @NotBlank(message = "The password is required")
    @Pattern(regexp = "[0-9a-zA-Z]+", message = "Password should contain numbers, upper and lower case letters only")
    @Size(min = 8, max = 40, message = "Invalid size for password")
    private String pwd;
}
