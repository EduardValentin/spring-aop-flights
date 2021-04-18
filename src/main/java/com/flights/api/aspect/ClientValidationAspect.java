package com.flights.api.aspect;

import com.flights.api.model.Client;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Aspect
@Component
public class ClientValidationAspect {

    private static final Logger logger = LoggerFactory.getLogger(ClientValidationAspect.class);

    @Before("execution(* com.flights.api.service.ClientService.create(com.flights.api.model.Client)) && args(client,..)")
    public void validate(Client client) {

        int length = client.getCnp().length();
        if (length != 13) {
            logger.error("Incorrect size for identification number " + length + " for client with name: " + client.getNume());
            throw new ResponseStatusException(BAD_REQUEST);
        }
    }
}
