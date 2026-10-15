package br.com.consolidaiag.cycle.domain.exceptions;

import br.com.consolidaiag.shared.domain.exceptions.ResourceNotFoundException;

import java.util.UUID;

public final class WeeklyCycleNotFoundException extends ResourceNotFoundException {

    public WeeklyCycleNotFoundException(UUID cycleId) {
        super("Weekly cycle not found with ID: " + cycleId);
    }
}