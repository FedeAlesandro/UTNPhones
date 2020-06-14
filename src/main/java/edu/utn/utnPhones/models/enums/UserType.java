package edu.utn.utnPhones.models.enums;

import edu.utn.utnPhones.exceptions.NotFoundException;

import static edu.utn.utnPhones.utils.Constants.INVALID_USER_TYPE;

public enum UserType{

    employee, client, infrastructure;

    public static UserType getUserType(String userType){

        if (userType.equals(employee.toString())){

            return employee;
        } else if (userType.equals(client.toString())){

            return client;
        } else if (userType.equals(infrastructure.toString())){

            return infrastructure;
        } else {
            throw new NotFoundException(INVALID_USER_TYPE);
        }
    }
}
