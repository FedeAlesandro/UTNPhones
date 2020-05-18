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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cities")

public class City {

    @Id
    @Column(name = "id_city")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_province", referencedColumnName = "id_province")
    private Province province;

    @NotNull
    @Column(name = "city_name")
    private String name;

    @NotNull
    @Column(name = "area_code")
    private String areaCode;

    @OneToMany(mappedBy = "originCity")
    private List<Tariff> originTariffs;

    @OneToMany(mappedBy = "destinationCity")
    private List<Tariff> DestinationTariffs;
}
