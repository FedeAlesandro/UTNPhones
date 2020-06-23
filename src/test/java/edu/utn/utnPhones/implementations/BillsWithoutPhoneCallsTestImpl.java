package edu.utn.utnPhones.implementations;

import edu.utn.utnPhones.models.enums.BillStatus;
import edu.utn.utnPhones.models.projections.BillsWithoutPhoneCalls;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BillsWithoutPhoneCallsTestImpl implements BillsWithoutPhoneCalls {
    @Override
    public Integer getCallsAmount() {
        return 10;
    }

    @Override
    public BigDecimal getTotalCost() {
        return BigDecimal.valueOf(100);
    }

    @Override
    public BigDecimal getTotalPrice() {
        return BigDecimal.valueOf(200);
    }

    @Override
    public Date getBillDate() {
        return new GregorianCalendar(2020, Calendar.MARCH, 1).getTime();
    }

    @Override
    public Date getBillExpiration() {
        return new GregorianCalendar(2020, Calendar.MARCH, 16).getTime();
    }

    @Override
    public String getState() {
        return BillStatus.sent.toString();
    }
}
