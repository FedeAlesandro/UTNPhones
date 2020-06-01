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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
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
    public ResponseEntity<List<UsersWithoutPassword>> getAll(){
        List<UsersWithoutPassword> usersList = userService.getAll();
        return usersList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(usersList) : ResponseEntity.ok().body(usersList);
    }

    //Listar clientes no eliminados
    @GetMapping("/clients/")
    public ResponseEntity<List<ClientsWithoutPassword>> getClients(){
        List<ClientsWithoutPassword> clientsList = userService.getClients();
        return clientsList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(clientsList) : ResponseEntity.ok().body(clientsList);
    }

    @PostMapping("/") //Puse cualquier URI por ahora hasta que la creemos de verdad
    public ResponseEntity<UserDtoResponse> add(@RequestBody @Valid UserDtoAdd user){
        User addedUser = userService.add(user);
        ProvinceDto provinceDto = ProvinceDto.builder().name(addedUser.getCity().getProvince().getName()).build();
        CityDto cityDto = CityDto.builder().name(addedUser.getCity().getName()).areaCode(addedUser.getCity().getAreaCode()).province(provinceDto).build();
        return ResponseEntity.created(URI.create("Localhost:8081")).body(UserDtoResponse.builder()
                    .id(addedUser.getId())
                    .name(addedUser.getName())
                    .lastName(addedUser.getLastName())
                    .dni(addedUser.getDni())
                    .userName(addedUser.getUserName())
                    .city(cityDto).build());
    }

    //Eliminar cliente
    @DeleteMapping("/{idUser}/")
    public ResponseEntity<?> remove(@PathVariable(value = "idUser") Integer idUser){
        userService.remove(idUser);
        return ResponseEntity.ok().build();
    }

    /*@PutMapping("/{idUser}/")
    public ResponseEntity<UserDtoResponse> update(@PathVariable(value = "idUser") Integer idUser, @RequestBody @Valid UserDtoPut user){
        userService.update(idUser, user);
        return ResponseEntity.ok().body(UserDtoResponse.builder()
                    .name(user.getName())
                    .lastName(user.getLastName())
                    .dni(user.getDni())
                    .userName(user.getUserName())
                    .city(user.getCity())
                    .build());
    }*/

    /*@PatchMapping("/{idUser}/")
    public ResponseEntity<UserDtoResponse> partialUpdate(@PathVariable(value = "idUser") Integer idUser, @RequestBody @Valid UserDtoPatch user){
        User updatedUser = userService.partialUpdate(idUser, user);
        return ResponseEntity.ok().body(UserDtoResponse.builder()
                    .name(updatedUser.getName())
                    .lastName(updatedUser.getLastName())
                    .dni(updatedUser.getDni())
                    .userName(updatedUser.getUserName())
                    .city(updatedUser.getCity())
                    .build());
    }*/
}
