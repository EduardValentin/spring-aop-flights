package com.flights.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RezervareDto {

    @NotNull
    private Integer idZbor;

    @NotNull
    private ClientDto client;

    @NotNull
    private Integer idLoc;

    @NotNull
    private Double pret;

    @NotNull
    private Double discount;

    @NotBlank
    private String status;
}
