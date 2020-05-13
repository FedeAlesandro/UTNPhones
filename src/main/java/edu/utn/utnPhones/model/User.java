package edu.utn.utnPhones.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;
    @NotNull
    private City city;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    private String dni;
    @NotNull
    private String userName;
    @NotNull
    private String pwd;
    @NotNull
    private String userType;
    @NotNull
    private Boolean removedUser;
}
