package edu.utn.utnPhones.exceptions;

public class UnauthorizedUserTypeException extends RuntimeException {

    public UnauthorizedUserTypeException(String message){
        super(message);
    }
}
