package edu.utn.utnPhones.models.dtos.responses;

import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.dtos.CityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoResponse {

    private Integer id;

    private String name;

    private String lastName;

    private String dni;

    private String userName;

    private CityDto city;

    public static UserDtoResponse fromUser(User user){

        return UserDtoResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .dni(user.getDni())
                .userName(user.getUserName())
                .city(CityDto.fromCity(user.getCity()))
                .build();
    }
}
