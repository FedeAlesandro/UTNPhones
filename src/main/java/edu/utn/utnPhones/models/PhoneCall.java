package edu.utn.utnPhones.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "phone_calls")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PhoneCall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_phone_call")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_origin_phone_line", referencedColumnName = "id_phone_line")
    @JsonBackReference(value = "callOriginPhoneLine")
    private PhoneLine originPhoneLine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_destination_phone_line", referencedColumnName = "id_phone_line")
    @JsonBackReference(value = "callDestinationPhoneLine")
    private PhoneLine destinationPhoneLine;

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

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @NotBlank
    @Column(name = "origin_phone_number")
    private String originPhoneNumber;

    @NotBlank
    @Column(name = "destination_phone_number")
    private String destinationPhoneNumber;

    //@NotNull
    @JsonFormat(timezone = "GMT-03:00", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    @Column(name = "date_call")
    private Date date;

}
