package edu.utn.utnPhones.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.utn.utnPhones.models.dtos.requests.UserDtoAdd;
import edu.utn.utnPhones.models.dtos.requests.UserDtoPut;
import edu.utn.utnPhones.models.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
@Builder
public class User {

    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_city", referencedColumnName = "id_city")
    @JsonBackReference(value = "userCity")
    private City city;

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "[a-zA-Z]+", message = "Name should contain only letters")
    @Size(min = 2, max = 40, message = "Invalid size for name")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "[a-zA-Z]+", message = "Last name should contain only letters")
    @Size(min = 2, max = 40, message = "Invalid size for last name")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "DNI is required")
    @Pattern(regexp = "[0-9]+", message = "DNI should be made up of numbers")
    @Size(min = 7, max = 8, message = "Invalid size for DNI")
    @Column(name = "dni")
    private String dni;

    @NotBlank(message = "Username is required")
    @Pattern(regexp = "[0-9a-zA-Z\\.\\-\\_]+", message = "Invalid username, choose another one")
    @Size(min = 8, max = 40, message = "Invalid size for username")
    @Column(name = "user_name")
    private String userName;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "[0-9a-zA-Z]+", message = "Password should contain numbers, upper and lower case letters only")
    @Size(min = 4, max = 40, message = "Invalid size for password")
    @Column(name = "pwd")
    private String pwd;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @NotNull
    @Column(name = "removed_user")
    private Boolean removed;

    @OneToMany(mappedBy = "user")
    private List<PhoneLine> phoneLines;

    public static User fromUserDtoAdd(UserDtoAdd userDtoAdd, City city){

        return User.builder()
                .name(userDtoAdd.getName())
                .lastName(userDtoAdd.getLastName())
                .dni(userDtoAdd.getDni())
                .userName(userDtoAdd.getUserName())
                .pwd(userDtoAdd.getPwd())
                .userType(UserType.getUserType(userDtoAdd.getUserType()))
                .city(city)
                .removed(false)
                .phoneLines(null)
                .build();
    }

    public static User fromUserDtoPut(UserDtoPut userDtoPut, User oldUser, City city){

        return User.builder()
                .id(oldUser.getId())
                .city(city)
                .name(userDtoPut.getName())
                .lastName(userDtoPut.getLastName())
                .dni(userDtoPut.getDni())
                .userName(userDtoPut.getUserName())
                .pwd(userDtoPut.getPwd())
                .removed(oldUser.getRemoved())
                .userType(oldUser.getUserType())
                .phoneLines(oldUser.getPhoneLines())
                .build();
    }
}
