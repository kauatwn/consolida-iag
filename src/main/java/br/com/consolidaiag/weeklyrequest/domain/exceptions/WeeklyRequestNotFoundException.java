package br.com.consolidaiag.weeklyrequest.domain.exceptions;

import br.com.consolidaiag.shared.DomainException;

import java.util.UUID;

public final class WeeklyRequestNotFoundException extends DomainException {

    public WeeklyRequestNotFoundException(UUID requestId) {
        super(String.format("Weekly request not found with ID: %s", requestId));
    }
}