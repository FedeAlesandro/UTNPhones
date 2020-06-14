package edu.utn.utnPhones.models.projections;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public interface BillsWithoutPhoneCalls {

    Integer getCallsAmount();

    BigDecimal getTotalCost();

    BigDecimal getTotalPrice();

    @JsonFormat(timezone = "GMT-03:00", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    Date getBillDate();

    @JsonFormat(timezone = "GMT-03:00", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    Date getBillExpiration();

    String getState();
}
