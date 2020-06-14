package edu.utn.utnPhones.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.utn.utnPhones.models.enums.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "bills")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bill")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_phone_line", referencedColumnName = "id_phone_line")
    @JsonBackReference(value = "billPhoneLine")
    private PhoneLine phoneLine;

    @OneToMany(mappedBy = "bill")
    private List<PhoneCall> calls;

    @Column(name = "calls_amount")
    private Integer callsAmount;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "bill_date")
    private LocalDate date;

    @Column(name = "bill_expiration")
    private LocalDate dateExpiration;

    @Enumerated(EnumType.STRING)
    private BillStatus state;

}
