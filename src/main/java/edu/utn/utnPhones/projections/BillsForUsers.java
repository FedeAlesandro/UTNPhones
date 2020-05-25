package edu.utn.utnPhones.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface BillsForUsers {

    String getUsername();

    Integer getCallsAmount();

    BigDecimal getTotalPrice();

    LocalDate getDate();

    LocalDate getDateExpiration();
}
