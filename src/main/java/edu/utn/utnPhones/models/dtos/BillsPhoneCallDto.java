package edu.utn.utnPhones.models.dtos;

import edu.utn.utnPhones.models.PhoneCall;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillsPhoneCallDto {

    private Integer id;

    private BigDecimal totalPrice;
    
    public BillsPhoneCallDto(PhoneCall phoneCall){
        id = phoneCall.getId();
        totalPrice = phoneCall.getTotalPrice();
    }
}
