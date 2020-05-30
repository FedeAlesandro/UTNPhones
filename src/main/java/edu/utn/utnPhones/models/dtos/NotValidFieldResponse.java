package edu.utn.utnPhones.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotValidFieldResponse {

    private String field;
    private String message;

}
