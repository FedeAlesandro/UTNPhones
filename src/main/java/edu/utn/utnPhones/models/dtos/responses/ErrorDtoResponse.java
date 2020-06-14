package edu.utn.utnPhones.models.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDtoResponse {

    private String message;

    private Integer errorCode;
}
