package com.asgr.minderest.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClientDoesNotExistException extends ResponseStatusException {

    public static final String MESSAGE_FMT = "Client with code '%s' does not exist";

    public ClientDoesNotExistException(String clientId) {
        super(HttpStatus.NOT_FOUND, String.format(MESSAGE_FMT, clientId));
    }

    public ClientDoesNotExistException(String clientId, Throwable cause) {
        super(HttpStatus.NOT_FOUND, String.format(MESSAGE_FMT, clientId), cause);
    }

}
