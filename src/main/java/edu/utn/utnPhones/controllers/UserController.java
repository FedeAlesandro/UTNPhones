package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.dtos.UserDtoAdd;
import edu.utn.utnPhones.models.dtos.UserDtoPatch;
import edu.utn.utnPhones.models.dtos.UserDtoPut;
import edu.utn.utnPhones.models.projections.ClientsWithoutPassword;
import edu.utn.utnPhones.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Listar clientes no eliminados
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
