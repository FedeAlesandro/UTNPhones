package edu.utn.utnPhones.models.dtos;

import edu.utn.utnPhones.models.Bill;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillsPhoneLineDto {

    private Integer id;

    private BigDecimal totalPrice;

    public BillsPhoneLineDto(Bill bill){
        id = bill.getId();
        totalPrice = bill.getTotalPrice();
    }
}
