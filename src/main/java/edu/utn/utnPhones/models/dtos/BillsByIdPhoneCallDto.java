package edu.utn.utnPhones.models.dtos;

import edu.utn.utnPhones.models.PhoneCall;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillsByIdPhoneCallDto {

    private Integer id;

    private Integer duration;

    private BigDecimal totalPrice;

    private BigDecimal totalCost;

    public BillsByIdPhoneCallDto(PhoneCall phoneCall){
        id = phoneCall.getId();
        duration = phoneCall.getDuration();
        totalPrice = phoneCall.getTotalPrice();
        totalCost = phoneCall.getTotalCost();
    }
}
