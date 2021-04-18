package com.flights.api.service;

import com.flights.api.annotation.LogExecution;
import com.flights.api.dto.AvionViewDto;
import com.flights.api.dto.CreateAvionDto;
import com.flights.api.dto.LocViewDto;
import com.flights.api.mappings.DTOMapper;
import com.flights.api.mappings.EntityMapper;
import com.flights.api.model.Avion;
import com.flights.api.model.MarcaAvion;
import com.flights.api.repository.AvionRepository;
import com.flights.api.repository.LocRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class AvionService {

    private static final Logger logger = LoggerFactory.getLogger(AvionService.class);

    private final AvionRepository avionRepository;
    private final EntityMapper entityMapper;
    private final DTOMapper dtoMapper;
    private final LocRepository locRepository;

    public AvionService(AvionRepository avionRepository, EntityMapper entityMapper, DTOMapper dtoMapper, LocRepository locRepository) {
        this.avionRepository = avionRepository;
        this.entityMapper = entityMapper;
        this.dtoMapper = dtoMapper;
        this.locRepository = locRepository;
    }

    public Optional<Avion> getAirplane(int id) {

        return avionRepository.findById(id);
    }

    public List<AvionViewDto> getAllAirplanes() {
        return avionRepository.findAll().stream().map(dtoMapper::toAvionDTO).collect(toList());
    }

    @LogExecution
    public AvionViewDto addAirplane(CreateAvionDto avionDto, MarcaAvion marcaAvion) {

        return dtoMapper.toAvionDTO(add(avionDto, marcaAvion));
    }

    public List<LocViewDto> getSeats(Avion avion) {

        return dtoMapper.mapList(locRepository.findAllByAvion(avion), dtoMapper::toLocViewDto);
    }


    public Avion add(CreateAvionDto avionDto, MarcaAvion marcaAvion) {


        Avion avion = entityMapper.toAvion(avionDto, marcaAvion);

        return avionRepository.save(avion);
    }
}
