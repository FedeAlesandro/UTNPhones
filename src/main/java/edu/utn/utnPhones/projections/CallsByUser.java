package edu.utn.utnPhones.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CallsByUser {

    String getUsername();

    BigDecimal getTotalCost();

    BigDecimal getTotalPrice();

    Integer getDuration();

    LocalDate getDate();
}
