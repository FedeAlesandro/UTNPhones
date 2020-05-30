package edu.utn.utnPhones.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "phone_calls")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PhoneCall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_phone_call")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_origin_phone_line", referencedColumnName = "id_phone_line")
    @JsonBackReference(value = "callOriginPhoneLine")
    private PhoneLine originPhoneLine;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_destination_phone_line", referencedColumnName = "id_phone_line")
    @JsonBackReference(value = "callDestinationPhoneLine")
    private PhoneLine destinationPhoneLine;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_tariff", referencedColumnName = "id_tariff")
    @JsonBackReference(value = "callTariff")
    private Tariff tariff;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_bill", referencedColumnName = "id_bill")
    @JsonBackReference(value = "callBill")
    private Bill bill;

    @NotNull
    @Column(name = "duration")
    private Integer duration;

    //@NotNull
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    //@NotNull
    @Column(name = "total_cost")
    private BigDecimal totalCost;

    //@NotNull
    @Column(name = "origin_phone_number")
    private String originPhoneNumber;

    //@NotNull
    @Column(name = "destination_phone_number")
    private String destinationPhoneNumber;

    @NotNull
    @Column(name = "date_call")
    private LocalDate date;

}
