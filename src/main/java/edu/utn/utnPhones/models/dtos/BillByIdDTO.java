package edu.utn.utnPhones.models.dtos;

import edu.utn.utnPhones.models.Bill;
import edu.utn.utnPhones.models.BillStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class BillByIdDTO {

    private Integer id;

    private List<BillsByIdPhoneCallDto> calls;

    private Integer callsAmount;

    private BigDecimal totalCost;

    private BigDecimal totalPrice;

    private LocalDate date;

    private LocalDate dateExpiration;

    private BillStatus state;

    public BillByIdDTO(Bill bill, List<BillsByIdPhoneCallDto> calls) {
        id = bill.getId();
        this.calls = calls;
        callsAmount = bill.getCallsAmount();
        totalCost = bill.getTotalCost();
        totalPrice = bill.getTotalPrice();
        date = bill.getDate();
        dateExpiration = bill.getDateExpiration();
        state = bill.getState();
    }

}
