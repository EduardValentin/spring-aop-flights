package com.flights.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AvionViewDto {

    private int id = 0;
    private double greutateTone;
    private int locuriMaxime;
    private MarcaDto marca;
    private List<LocViewDto> locuri;
}
