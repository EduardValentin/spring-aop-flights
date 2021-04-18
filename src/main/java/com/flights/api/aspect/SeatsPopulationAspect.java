package com.flights.api.aspect;

import com.flights.api.dto.AvionViewDto;
import com.flights.api.dto.CreateAvionDto;
import com.flights.api.model.Avion;
import com.flights.api.model.Clasa;
import com.flights.api.model.Loc;
import com.flights.api.model.MarcaAvion;
import com.flights.api.repository.AvionRepository;
import com.flights.api.repository.ClasaRepository;
import com.flights.api.repository.LocRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Aspect
@Component
public class SeatsPopulationAspect {

    private final String DENUMIRE_CLASA = "Clasa I";

    private static final Logger logger = LoggerFactory.getLogger(SeatsPopulationAspect.class);

    private final LocRepository locRepository;
    private final ClasaRepository clasaRepository;
    private final AvionRepository avionRepository;

    public SeatsPopulationAspect(LocRepository locRepository, ClasaRepository clasaRepository, AvionRepository avionRepository) {

        this.locRepository = locRepository;
        this.clasaRepository = clasaRepository;
        this.avionRepository = avionRepository;
    }

    @Around(value = "execution(* com.flights.api.service.AvionService.addAirplane(com.flights.api.dto.CreateAvionDto, com.flights.api.model.MarcaAvion)) " +
            "&& args(avionDto, marcaAvion,..)", argNames = "proceedingJoinPoint,avionDto,marcaAvion")
    public Object populate(ProceedingJoinPoint proceedingJoinPoint, CreateAvionDto avionDto, MarcaAvion marcaAvion) throws Throwable {

        AvionViewDto avion = (AvionViewDto) proceedingJoinPoint.proceed();

        Avion avionModel = avionRepository.findById(avion.getId())
                .orElseThrow(() -> new IllegalStateException("The plane with id: " + avion.getId() + " was not found"));

        logger.info("Populating seats for plane with brand name: " + marcaAvion.getNume());

        Clasa clasa = clasaRepository.findByDenumire(DENUMIRE_CLASA)
                .orElseThrow(() -> new RuntimeException("Class: " + DENUMIRE_CLASA + " not present"));

        List<Loc> locuri = IntStream.range(0, avionDto.getLocuriMaxime())
                .mapToObj(loc -> Loc.builder()
                .avion(avionModel)
                .nrLoc(loc)
                .clasa(clasa)
                .build())
                .collect(toList());

        locRepository.saveAll(locuri);

        return avion;
    }
}
