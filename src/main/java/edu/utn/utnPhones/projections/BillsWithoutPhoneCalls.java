package edu.utn.utnPhones.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface BillsWithoutPhoneCalls {

    Integer getCallsAmount();

    BigDecimal getTotalCost();

    BigDecimal getTotalPrice();

    LocalDate getBillDate();

    LocalDate getBillExpiration();

    String getState();
}
