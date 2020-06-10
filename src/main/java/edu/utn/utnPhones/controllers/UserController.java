package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.dtos.CityDto;
import edu.utn.utnPhones.models.dtos.ProvinceDto;
import edu.utn.utnPhones.models.dtos.UserDtoAdd;
import edu.utn.utnPhones.models.dtos.UserDtoPatch;
import edu.utn.utnPhones.models.dtos.UserDtoPut;
import edu.utn.utnPhones.models.dtos.UserDtoResponse;
import edu.utn.utnPhones.models.projections.ClientsWithoutPassword;
import edu.utn.utnPhones.models.projections.UsersWithoutPassword;
import edu.utn.utnPhones.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.net.URI;
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
}
