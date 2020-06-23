package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.exceptions.AlreadyExistsException;
import edu.utn.utnPhones.exceptions.DuplicatedUsernameException;
import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.exceptions.UnauthorizedUserTypeException;
import edu.utn.utnPhones.models.dtos.responses.ErrorDtoResponse;
import edu.utn.utnPhones.models.dtos.responses.NotValidDtoResponse;
import edu.utn.utnPhones.models.dtos.responses.NotValidFieldDtoResponse;
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
    public ResponseEntity<NotValidDtoResponse> handleNotValidException(MethodArgumentNotValidException exception){

        List<FieldError> errors = exception.getBindingResult().getFieldErrors();

        List<NotValidFieldDtoResponse> responses = errors.stream()
                .map(e -> NotValidFieldDtoResponse.builder()
                        .field(e.getField())
                        .message(e.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(NotValidDtoResponse.builder()
                        .errors(responses)
                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDtoResponse> handleNotFoundException(NotFoundException exception){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDtoResponse.builder()
                        .message(exception.getMessage())
                        .errorCode(1)
                        .build());
    }

    @ExceptionHandler(DuplicatedUsernameException.class)
    public ResponseEntity<ErrorDtoResponse> handleDuplicatedUsernameException(DuplicatedUsernameException exception){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDtoResponse.builder()
                        .errorCode(2)
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorDtoResponse> handleAlreadyExistsException(AlreadyExistsException exception){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDtoResponse.builder()
                        .errorCode(3)
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(UnauthorizedUserTypeException.class)
    public ResponseEntity<ErrorDtoResponse> handleUnauthorizedUserTypeException(UnauthorizedUserTypeException exception){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDtoResponse.builder()
                        .errorCode(4)
                        .message(exception.getMessage())
                        .build());
    }
}
