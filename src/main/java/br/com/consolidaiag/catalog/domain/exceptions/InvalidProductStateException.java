package br.com.consolidaiag.catalog.domain.exceptions;

import br.com.consolidaiag.shared.domain.exceptions.BusinessRuleException;

public final class InvalidProductStateException extends BusinessRuleException {

    public InvalidProductStateException(String message) {
        super(message);
    }
}