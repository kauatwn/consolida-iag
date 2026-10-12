package br.com.consolidaiag.cycle.domain.exceptions;

import br.com.consolidaiag.shared.DomainException;

public final class InvalidCycleDateException extends DomainException {

    public InvalidCycleDateException() {
        super("The cycle end date cannot be before the start date.");
    }

    public InvalidCycleDateException(String message) {
        super(message);
    }
}