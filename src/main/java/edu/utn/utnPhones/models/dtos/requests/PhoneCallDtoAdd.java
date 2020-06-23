package edu.utn.utnPhones.models.dtos.requests;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Data
public class PhoneCallDtoAdd {

    @NotNull(message = "The call duration is required")
    @Min(value = 0, message = "Incorrect duration")
    private Integer duration;

    @NotBlank(message = "An origin phone number is required")
    @Pattern(regexp = "[0-9]+", message = "The phone number must contain only numbers")
    @Size(min = 6, max = 32, message = "Invalid size for phone number")
    private String originPhoneNumber;

    @NotBlank(message = "A destination phone number is required")
    @Pattern(regexp = "[0-9]+", message = "The phone number must contain only numbers")
    @Size(min = 6, max = 32, message = "Invalid size for phone number")
    private String destinationPhoneNumber;

}
