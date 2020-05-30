package edu.utn.utnPhones.models.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NotValidResponse {

    private List<NotValidFieldResponse> errors;
}
