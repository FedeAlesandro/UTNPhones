package edu.utn.utnPhones.session;

import edu.utn.utnPhones.models.User;
import lombok.Data;

import java.util.Date;

@Data
public class Session {

    String token;
    User loggedUser;
    Date lastAction;

    public Session(String token, User loggedUser, Date lastAction) {
        this.token = token;
        this.loggedUser = loggedUser;
        this.lastAction = lastAction;
    }
}