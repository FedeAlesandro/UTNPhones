package edu.utn.utnPhones.models.dtos;

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
}
