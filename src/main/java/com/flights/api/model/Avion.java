package com.flights.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity(name = "AVION")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Avion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "avion_seq")
    @SequenceGenerator(name = "avion_seq", allocationSize = 1)
    private int idAvion;

    private double greutateTone;
    private int locuriMaxime;

    @ManyToOne
    @JoinColumn(name = "id_marca_avion")
    private MarcaAvion marcaAvion;

    @OneToMany(mappedBy = "avion")
    private Set<Zbor> zboruri;

    @OneToMany(mappedBy = "avion")
    private Set<CapacitateClasa> capacitateClase;

    @OneToMany(mappedBy = "avion")
    private List<Loc> locuri;
}
