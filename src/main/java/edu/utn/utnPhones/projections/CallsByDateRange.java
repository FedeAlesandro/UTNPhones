package edu.utn.utnPhones.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CallsByDateRange {

    String getUsername();

    Integer getId();

    BigDecimal getTotalCost();

    BigDecimal getTotalPrice();

    Integer duration();

    LocalDate date();
}
