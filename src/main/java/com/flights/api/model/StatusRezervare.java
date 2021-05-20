package com.flights.api.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity(name = "status_rezervare")
@Getter
public class StatusRezervare {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idStatus;

    private String denumire;

    @OneToMany(mappedBy = "statusRezervare")
    private Set<Rezervare> rezervari;
}
