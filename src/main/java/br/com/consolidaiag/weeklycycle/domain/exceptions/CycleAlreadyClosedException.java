package br.com.consolidaiag.weeklycycle.domain.exceptions;

import br.com.consolidaiag.shared.domain.exceptions.ConflictException;

public final class CycleAlreadyClosedException extends ConflictException {

    public CycleAlreadyClosedException() {
        super("This cycle is already closed.");
    }
}