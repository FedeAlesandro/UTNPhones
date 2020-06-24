package edu.utn.utnPhones.models.projections;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public interface BillsForUsers {

    String getUsername();

    Integer getCallsAmount();

    BigDecimal getTotalPrice();

    @JsonFormat(timezone = "GMT-03:00", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    Date getDate();

    @JsonFormat(timezone = "GMT-03:00", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    Date getDateExpiration();

    void setUsername();

    void setCallsAmount();

    void setTotalPrice();

    void setDate();

    void setDateExpiration();
}
