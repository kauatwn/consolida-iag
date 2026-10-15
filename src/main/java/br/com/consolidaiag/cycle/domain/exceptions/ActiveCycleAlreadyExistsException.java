package br.com.consolidaiag.cycle.domain.exceptions;

import br.com.consolidaiag.shared.domain.exceptions.ConflictException;

public final class ActiveCycleAlreadyExistsException extends ConflictException {

    public ActiveCycleAlreadyExistsException() {
        super("Cannot open a new purchasing cycle while another cycle is still open (RN-02.8).");
    }
}