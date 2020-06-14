package edu.utn.utnPhones.models.dtos.responses;

import edu.utn.utnPhones.models.Tariff;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TariffDtoResponse {

    private String originCity;

    private String destinationCity;

    private Float pricePerMinute;

    private Float costPerMinute;

    public static TariffDtoResponse fromTariff(Tariff tariff){

        return TariffDtoResponse.builder()
                .originCity(tariff.getOriginCity().getName())
                .destinationCity(tariff.getDestinationCity().getName())
                .pricePerMinute(tariff.getPricePerMinute())
                .costPerMinute(tariff.getCostPerMinute())
                .build();
    }
}
