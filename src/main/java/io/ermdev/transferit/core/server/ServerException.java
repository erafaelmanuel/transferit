package io.ermdev.transferit.core.server;

import io.ermdev.transferit.core.protocol.ProtocolException;

public class ServerException extends ProtocolException {

    public ServerException(String message) {
        super(message);
    }
}
