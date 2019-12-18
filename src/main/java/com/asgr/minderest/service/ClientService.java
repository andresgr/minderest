package com.asgr.minderest.service;

import com.asgr.minderest.model.Client;
import com.asgr.minderest.repository.ClientRepository;
import com.asgr.minderest.service.BaseCrudService;
import com.asgr.minderest.service.ClientDoesNotExistException;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Optional;

@NoArgsConstructor
@Component
@Accessors(fluent = true)
@Slf4j
public class ClientService extends BaseCrudService<Client, ClientRepository> implements Serializable {

    private final long serialVersionUID = 0L;

    @Autowired
    @Getter
    ClientRepository repository;

    public Client findByClientId(@NotNull @NotBlank String clientId) throws ClientDoesNotExistException {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(clientId), "clientId cannot be null or empty");

        log.debug("Finding client with it '{}'", clientId);
        return Optional.ofNullable(repository.findByClientId(clientId))
                .orElseThrow(() -> new ClientDoesNotExistException(clientId));
    }
}