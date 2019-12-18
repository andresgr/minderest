package com.asgr.minderest.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClientProductDoesNotExistException extends ResponseStatusException {

    public static final String MESSAGE_FMT = "Client product with name '%s' does not exist";

    public ClientProductDoesNotExistException(String productName) {
        super(HttpStatus.NOT_FOUND, String.format(MESSAGE_FMT, productName));
    }

    public ClientProductDoesNotExistException(String productName, Throwable cause) {
        super(HttpStatus.NOT_FOUND, String.format(MESSAGE_FMT, productName), cause);
    }

}
