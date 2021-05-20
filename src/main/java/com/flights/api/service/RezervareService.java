package com.flights.api.service;

import com.flights.api.dto.ClientDto;
import com.flights.api.dto.RezervareDto;
import com.flights.api.dto.RezervareViewDto;
import com.flights.api.mappings.DTOMapper;
import com.flights.api.mappings.EntityMapper;
import com.flights.api.model.Avion;
import com.flights.api.model.Client;
import com.flights.api.model.Loc;
import com.flights.api.model.Rezervare;
import com.flights.api.model.StatusRezervare;
import com.flights.api.model.Zbor;
import com.flights.api.repository.AvionRepository;
import com.flights.api.repository.ClientRepository;
import com.flights.api.repository.LocRepository;
import com.flights.api.repository.RezervareRepository;
import com.flights.api.repository.StatusRezervareRepository;
import com.flights.api.repository.ZborRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class RezervareService {

    private final RezervareRepository rezervareRepository;
    private final ClientRepository clientRepository;
    private final ZborRepository zborRepository;
    private final LocRepository locRepository;
    private final AvionRepository avionRepository;
    private final DTOMapper dtoMapper;
    private final EntityMapper entityMapper;
    private final ClientService clientService;
    private final StatusRezervareRepository statusRezervareRepository;

    public RezervareService(RezervareRepository rezervareRepository, ClientRepository clientRepository,
                            ZborRepository zborRepository, LocRepository locRepository, AvionRepository avionRepository,
                            DTOMapper dtoMapper, EntityMapper entityMapper, ClientService clientService,
                            StatusRezervareRepository statusRezervareRepository) {

        this.rezervareRepository = rezervareRepository;
        this.clientRepository = clientRepository;
        this.zborRepository = zborRepository;
        this.locRepository = locRepository;
        this.avionRepository = avionRepository;
        this.dtoMapper = dtoMapper;
        this.entityMapper = entityMapper;
        this.clientService = clientService;
        this.statusRezervareRepository = statusRezervareRepository;
    }

    public List<RezervareViewDto> findAll() {

        return dtoMapper.mapList(rezervareRepository.findAll(), dtoMapper::toRezervareViewDto);
    }

    public RezervareViewDto create(RezervareDto rezervareDto) {

        ClientDto clientDto = rezervareDto.getClient();

        Zbor zbor = zborRepository.findById(rezervareDto.getIdZbor())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Loc loc = locRepository.findById(rezervareDto.getIdLoc())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Client client = clientRepository.findByCnp(rezervareDto.getClient().getCnp())
                .orElseGet(() -> {
                    Client clientToCreate = Client.builder()
                            .actIdentitate(clientDto.getActIdentitate())
                            .cnp(clientDto.getCnp())
                            .email(clientDto.getEmail())
                            .nume(clientDto.getNume())
                            .prenume(clientDto.getPrenume())
                            .telefon(clientDto.getTelefon())
                            .build();

                    return clientService.create(clientToCreate);
                });

        StatusRezervare statusRezervare = statusRezervareRepository.findByDenumire(rezervareDto.getStatus())
                .orElseThrow(() -> {
                    log.warn("Status: " + rezervareDto.getStatus() + " not found in database");
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST);
                });

        Rezervare rezervare = Rezervare.builder()
                .client(client)
                .discount(rezervareDto.getDiscount())
                .loc(loc)
                .pret(rezervareDto.getPret())
                .statusRezervare(statusRezervare)
                .zbor(zbor)
                .data(LocalDateTime.now())
                .build();

        rezervare = rezervareRepository.save(rezervare);

        return dtoMapper.toRezervareViewDto(rezervare);
    }
}
