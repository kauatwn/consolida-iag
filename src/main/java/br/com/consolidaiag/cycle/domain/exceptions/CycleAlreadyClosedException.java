package br.com.consolidaiag.cycle.domain.exceptions;

import br.com.consolidaiag.shared.DomainException;

public final class CycleAlreadyClosedException extends DomainException {

    public CycleAlreadyClosedException() {
        super("This cycle is already closed.");
    }

    public CycleAlreadyClosedException(String message) {
        super(message);
    }
}