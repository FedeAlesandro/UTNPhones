package edu.utn.utnPhones.model;

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

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_origin_city", referencedColumnName = "id_city")
    @JsonBackReference(value = "tariffOriginCity")
    private City originCity;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_destination_city", referencedColumnName = "id_city")
    @JsonBackReference(value = "tariffDestinationCity")
    private City destinationCity;

    @NotNull
    @Column(name = "price_per_minute")
    private Float pricePerMinute;

    @NotNull
    @Column(name = "cost_per_minute")
    private Float costPerMinute;

}
