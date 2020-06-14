package edu.utn.utnPhones.models.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotValidFieldDtoResponse {

    private String field;

    private String message;
}
