package edu.utn.utnPhones.projections;

import edu.utn.utnPhones.model.BillStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface BillsByDateRange {

    String getUsername();

    Integer getId();

    Integer callsAmount();

    BigDecimal getTotalCost();

    BigDecimal getTotalPrice();

    LocalDateTime getDate();

    LocalDateTime getDateExpiration();

    BillStatus getState();
}
