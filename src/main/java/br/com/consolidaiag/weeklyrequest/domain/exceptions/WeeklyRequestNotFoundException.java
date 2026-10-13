package br.com.consolidaiag.weeklyrequest.domain.exceptions;

import java.util.UUID;

public final class WeeklyRequestNotFoundException extends RuntimeException {

    public WeeklyRequestNotFoundException(UUID requestId) {
        super(String.format("Weekly request not found with ID: %s", requestId));
    }
}