package edu.utn.utnPhones.models.dtos;

import edu.utn.utnPhones.models.Bill;
import edu.utn.utnPhones.models.BillStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class BillsByPhoneLine {

    private Integer id;

    private List<BillsPhoneCallDto> calls;

    private BigDecimal totalPrice;

    private LocalDate date;

    private LocalDate dateExpiration;

    private BillStatus state;

    public BillsByPhoneLine(Bill bill, List<BillsPhoneCallDto> calls) {
        id = bill.getId();
        this.calls = calls;
        totalPrice = bill.getTotalPrice();
        date = bill.getDate();
        dateExpiration = bill.getDateExpiration();
        state = bill.getState();
    }
}
