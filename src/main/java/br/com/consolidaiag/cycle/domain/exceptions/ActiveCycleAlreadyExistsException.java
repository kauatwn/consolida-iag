package br.com.consolidaiag.cycle.domain.exceptions;

import br.com.consolidaiag.shared.DomainException;

public final class ActiveCycleAlreadyExistsException extends DomainException {

    public ActiveCycleAlreadyExistsException() {
        super("Cannot open a new purchasing cycle while another cycle is still open (RN-02.8).");
    }

    public ActiveCycleAlreadyExistsException(String message) {
        super(message);
    }
}