package br.com.consolidaiag.cycle.domain.exceptions;

import br.com.consolidaiag.shared.DomainException;

import java.util.UUID;

public final class WeeklyCycleNotFoundException extends DomainException {

    public WeeklyCycleNotFoundException(UUID cycleId) {
        super("Weekly cycle not found with ID: " + cycleId);
    }

    public WeeklyCycleNotFoundException(String message) {
        super(message);
    }
}
