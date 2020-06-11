package edu.utn.utnPhones.models.dtos.responses;

import edu.utn.utnPhones.models.Tariff;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TariffResponseDto {

    private String originCity;

    private String destinationCity;

    private Float pricePerMinute;

    private Float costPerMinute;

    public TariffResponseDto(Tariff tariff){
        this.originCity = tariff.getOriginCity().getName();
        this.destinationCity = tariff.getDestinationCity().getName();
        this.pricePerMinute = tariff.getPricePerMinute();
        this.costPerMinute = tariff.getCostPerMinute();
    }
}
