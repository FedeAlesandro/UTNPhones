package edu.utn.utnPhones.controller;

import edu.utn.utnPhones.model.User;
import edu.utn.utnPhones.model.dto.UserUpdateDto;
import edu.utn.utnPhones.projections.ClientsWithoutPassword;
import edu.utn.utnPhones.projections.UsersWithoutPassword;
import edu.utn.utnPhones.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Listar usuarios
    @GetMapping("/")
    public List<UsersWithoutPassword> getAll(){
        return userService.getAll();
    }

    //Listar clientes no eliminados
    @GetMapping("/clients/")
    public List<ClientsWithoutPassword> getClients(){
        return userService.getClients();
    }

    @PostMapping("/")
    public void add(@RequestBody @Valid User user){
        userService.add(user);
    }

    //Eliminar cliente
    @DeleteMapping("/{idUser}/")
    public void remove(@PathVariable(value = "idUser") Integer idUser){
        userService.remove(idUser);
    }

    @PutMapping("/{idUser}/")
    public void update(@PathVariable(value = "idUser") Integer idUser, @RequestBody @Valid UserUpdateDto updatedUser){
        userService.update(idUser, updatedUser);
    }
}
