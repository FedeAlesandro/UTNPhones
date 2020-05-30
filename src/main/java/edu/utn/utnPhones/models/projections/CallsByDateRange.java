package edu.utn.utnPhones.models.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CallsByDateRange {

    String getUsername();

    BigDecimal getTotalPrice();

    Integer getDuration();

    LocalDate getDate();
}
