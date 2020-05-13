package edu.utn.utnPhones.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PhoneCall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPhoneCall;

    @NotNull
    private PhoneLine idOriginPhoneLine;

    @NotNull
    private PhoneLine idDestinationPhoneLine;

    @NotNull
    private Tariff tariff;

    private Bill bill;

    @NotNull
    private Integer duration;

    @NotNull
    private BigDecimal totalPrice;

    @NotNull
    private BigDecimal totalCost;

    @NotNull
    private String originPhoneNumber;

    @NotNull
    private String destinationPhoneNumber;

    @NotNull
    private LocalDate callDate;

    // TODO: 13/05/2020 bdd mapping annotations (@onetomany, ...) 
}
