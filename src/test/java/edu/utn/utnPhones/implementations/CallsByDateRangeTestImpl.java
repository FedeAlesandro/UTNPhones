package edu.utn.utnPhones.implementations;

import edu.utn.utnPhones.models.projections.CallsByDateRange;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CallsByDateRangeTestImpl implements CallsByDateRange {

    @Override
    public String getUsername() {
        return "carlitosTevez";
    }

    @Override
    public BigDecimal getTotalPrice() {
        return BigDecimal.valueOf(850);
    }

    @Override
    public Integer getDuration() {
        return 156;
    }

    @Override
    public Date getDate() {
        return new GregorianCalendar(2020, Calendar.MARCH, 1).getTime();
    }

    @Override
    public String getOriginNumber() {
        return "224510248";
    }

    @Override
    public String getDestinationNumber() {
        return "11657425";
    }
}
