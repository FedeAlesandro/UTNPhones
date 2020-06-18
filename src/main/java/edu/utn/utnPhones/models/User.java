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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_city", referencedColumnName = "id_city")
    @JsonBackReference(value = "userCity")
    private City city;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "dni")
    private String dni;

    @Column(name = "user_name")
    private String userName;
    
    @Column(name = "pwd")
    private String pwd;

    @Enumerated(EnumType.STRING)
    private UserType userType;

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
