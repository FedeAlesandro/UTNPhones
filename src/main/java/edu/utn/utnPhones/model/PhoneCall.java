package edu.utn.utnPhones.model;

import java.math.BigDecimal;

public class PhoneCall {

    private Integer idPhoneCall;
    private PhoneLine originPhoneLine;
    private PhoneLine destinationPhoneLine;
    private Tariff tariff;
    private Bill bill;
    private Integer duration;
    private BigDecimal totalPrice;
    private BigDecimal totalCost;
    private String originPhoneNumber;
    private String destinationPhoneNumber;
}
