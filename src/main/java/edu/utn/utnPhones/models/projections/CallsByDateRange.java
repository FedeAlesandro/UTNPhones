package edu.utn.utnPhones.models.projections;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public interface CallsByDateRange {

    String getUsername();

    BigDecimal getTotalPrice();

    Integer getDuration();

    @JsonFormat(timezone = "GMT-03:00", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    Date getDate();

    String getOriginNumber();

    String getDestinationNumber();
}
