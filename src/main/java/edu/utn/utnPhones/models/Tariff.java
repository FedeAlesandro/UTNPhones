package edu.utn.utnPhones.models;

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

@Entity
@Table(name = "tariffs")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tariff")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_origin_city", referencedColumnName = "id_city")
    private City originCity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_destination_city", referencedColumnName = "id_city")
    private City destinationCity;

    @Column(name = "price_per_minute")
    private Float pricePerMinute;

    @Column(name = "cost_per_minute")
    private Float costPerMinute;

}
