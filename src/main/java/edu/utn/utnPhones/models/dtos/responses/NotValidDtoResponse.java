package edu.utn.utnPhones.models.dtos.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NotValidDtoResponse {

    private List<NotValidFieldDtoResponse> errors;
}
