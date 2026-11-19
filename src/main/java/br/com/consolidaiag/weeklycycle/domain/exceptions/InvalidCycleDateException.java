package br.com.consolidaiag.weeklycycle.domain.exceptions;

import br.com.consolidaiag.shared.domain.exceptions.BusinessRuleException;

public final class InvalidCycleDateException extends BusinessRuleException {

    public InvalidCycleDateException() {
        super("The cycle end date cannot be before the start date.");
    }
}