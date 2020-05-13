package edu.utn.utnPhones.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBill;

    @NotNull
    private PhoneLine phoneLine;

    @NotNull
    private Integer callsAmount;

    @NotNull
    private BigDecimal totalCost;

    @NotNull
    private BigDecimal totalPrice;

    @NotNull
    private LocalDateTime billDate;

    @NotNull
    private LocalDateTime billDateExpiration;

    public enum BillStatus {

        payed, expired
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    private BillStatus state;

}
