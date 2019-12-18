package com.asgr.minderest.repository;

import com.asgr.minderest.model.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {

    Client findByClientId(String clientId);

}
