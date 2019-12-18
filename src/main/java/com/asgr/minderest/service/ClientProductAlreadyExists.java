package com.asgr.minderest.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClientProductAlreadyExists extends ResponseStatusException {

    public static final String MESSAGE_FMT = "Client '%s' product with name '%s' already exists";

    public ClientProductAlreadyExists(String clientId, String productName) {
        super(HttpStatus.CONFLICT, buildMessage(clientId, productName));
    }

    public ClientProductAlreadyExists(String clientId, String productName, Throwable cause) {
        super(HttpStatus.CONFLICT, buildMessage(clientId, productName), cause);
    }

    private static String buildMessage(String clientId, String productName) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(clientId), "ClientId cannot be null or empty");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(productName), "productName cannot be null or empty");

        return String.format(MESSAGE_FMT, clientId, productName);
    }

}
