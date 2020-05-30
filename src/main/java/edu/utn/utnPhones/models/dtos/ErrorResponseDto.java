package edu.utn.utnPhones.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {

    private String message;
    private Integer errorCode;
}
