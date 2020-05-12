package edu.utn.utnPhones.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PhoneCall {

    private Integer idPhoneCall;
    private Tariff tariff;
    private Bill bill;
    private Integer duration;
    private BigDecimal totalPrice;
    private BigDecimal totalCost;
    private String originPhoneNumber;
    private String destinationPhoneNumber;
    private LocalDate callDate;
}
