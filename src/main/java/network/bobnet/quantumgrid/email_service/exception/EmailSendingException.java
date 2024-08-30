package network.bobnet.quantumgrid.email_service.exception;

import network.bobnet.quantumgrid.commons.exceptions.AbstractApplicationException;

public class EmailSendingException extends AbstractApplicationException {

    public EmailSendingException(String message) {
        super(message);
    }
}
