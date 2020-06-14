package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.dtos.requests.UserDtoAdd;
import edu.utn.utnPhones.models.dtos.requests.UserDtoPatch;
import edu.utn.utnPhones.models.dtos.requests.UserDtoPut;
import edu.utn.utnPhones.models.projections.ClientsWithoutPassword;
import edu.utn.utnPhones.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    public List<ClientsWithoutPassword> getClients(){

        return userService.getClients();
    }

    public User getClient(String userName){

        return  userService.getClient(userName);
    }

    public User add(UserDtoAdd user){

        return userService.add(user);
    }

    public void remove(Integer idUser){

        userService.remove(idUser);
    }

    public User update(Integer idUser, UserDtoPut user){

        return userService.update(idUser, user);
    }

    public User partialUpdate(Integer idUser, UserDtoPatch user){

        return userService.partialUpdate(idUser, user);
    }

    public User login(String username, String pwd){

        return userService.login(username, pwd);
    }
}
