package edu.utn.utnPhones.models.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.utn.utnPhones.models.Bill;
import edu.utn.utnPhones.models.enums.BillStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class BillDtoResponse {

    private Integer id;

    private List<PhoneCallDtoResponse> calls;

    private Integer callsAmount;

    private BigDecimal totalCost;

    private BigDecimal totalPrice;

    @JsonFormat(timezone = "GMT-03:00", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date date;

    @JsonFormat(timezone = "GMT-03:00", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date dateExpiration;

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

    public static BillDtoResponse fromBillPayed(Bill bill) {

        return BillDtoResponse.builder()
                .id(bill.getId())
                .totalCost(bill.getTotalCost())
                .totalPrice(bill.getTotalPrice())
                .date(bill.getDate())
                .dateExpiration(bill.getDateExpiration())
                .state(bill.getState())
                .build();
    }
}
