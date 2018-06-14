package io.ermdev.transferit.core.client;

import io.ermdev.transferit.core.ProtocolException;

public class ClientException extends ProtocolException {

    public ClientException(String message) {
        super(message);
    }
}
