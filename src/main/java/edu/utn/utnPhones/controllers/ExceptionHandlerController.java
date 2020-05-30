package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.dtos.NotValidFieldResponse;
import edu.utn.utnPhones.models.dtos.NotValidResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<NotValidResponse> handleNotValidException(MethodArgumentNotValidException exception){

        List<FieldError> errors = exception.getBindingResult().getFieldErrors();
        List<NotValidFieldResponse> responses = errors
                .stream()
                .map(e -> NotValidFieldResponse.builder()
                        .field(e.getField())
                        .message(e.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity
                .badRequest()
                .body(NotValidResponse.builder()
                        .errors(responses)
                        .build());
    }
}
