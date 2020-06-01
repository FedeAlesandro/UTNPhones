package edu.utn.utnPhones.exceptions;

public class DuplicatedUsernameException extends RuntimeException {

    public DuplicatedUsernameException(String message){
        super(message);
    }

}
