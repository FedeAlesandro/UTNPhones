package edu.utn.utnPhones.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bill {

    private Integer idBill;
    private PhoneLine phoneLine;
    private Integer callsAmount;
    private BigDecimal totalCost;
    private BigDecimal totalPrice;
    private LocalDateTime billDate;
    private LocalDateTime billDateExpiration;
    private String state;
}
