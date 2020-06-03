package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.exceptions.DuplicatedUsernameException;
import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.exceptions.UserAlreadyExistsException;
import edu.utn.utnPhones.models.dtos.ErrorResponseDto;
import edu.utn.utnPhones.models.dtos.NotValidFieldResponse;
import edu.utn.utnPhones.models.dtos.NotValidResponse;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException exception){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDto.builder()
                .errorCode(1)
                .message(exception.getMessage())
                .build());
    }

    @ExceptionHandler(DuplicatedUsernameException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicatedUsernameException(DuplicatedUsernameException exception){

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponseDto.builder()
                .errorCode(2)
                .message(exception.getMessage())
                .build());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExistsException(UserAlreadyExistsException exception){

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponseDto.builder()
                .errorCode(3)
                .message(exception.getMessage())
                .build());
    }
}
