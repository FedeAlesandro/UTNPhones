package edu.utn.utnPhones.models.dtos.responses;

import edu.utn.utnPhones.models.Bill;
import edu.utn.utnPhones.models.enums.BillStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class BillDtoResponse {

    private Integer id;

    private List<PhoneCallDtoResponse> calls;

    private Integer callsAmount;

    private BigDecimal totalCost;

    private BigDecimal totalPrice;

    private LocalDate date;

    private LocalDate dateExpiration;

    private BillStatus state;

    public static BillDtoResponse fromBill(Bill bill, List<PhoneCallDtoResponse> calls) {

        return BillDtoResponse.builder()
                .id(bill.getId())
                .calls(calls)
                .totalCost(bill.getTotalCost())
                .totalPrice(bill.getTotalPrice())
                .date(bill.getDate())
                .dateExpiration(bill.getDateExpiration())
                .state(bill.getState())
                .build();
    }
}
