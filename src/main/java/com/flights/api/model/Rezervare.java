package com.flights.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "rezervare")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rezervare {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rezervare_seq")
    @SequenceGenerator(name = "rezervare_seq", allocationSize = 1)
    private int idRezervare;

    @ManyToOne
    @JoinColumn(name = "id_zbor")
    private Zbor zbor;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_loc")
    private Loc loc;

    @ManyToOne
    @JoinColumn(name = "id_status")
    private StatusRezervare statusRezervare;

    private double pret;
    private double discount;
    private LocalDateTime data;
}
