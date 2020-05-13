package edu.utn.utnPhones.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTariff;

    @NotNull
    private City originCity;

    @NotNull
    private City destinationCity;

    @NotNull
    private Float pricePerMinute;

    @NotNull
    private Float costPerMinute;

}
