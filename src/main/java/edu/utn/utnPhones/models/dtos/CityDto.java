package edu.utn.utnPhones.models.dtos;

import edu.utn.utnPhones.models.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityDto {

    private ProvinceDto province;

    private String name;

    private String areaCode;

    public static CityDto fromCity(City city){

        return CityDto.builder()
                .province(ProvinceDto.builder().name(city.getProvince().getName()).build())
                .areaCode(city.getAreaCode())
                .name(city.getName())
                .build();
    }
}
