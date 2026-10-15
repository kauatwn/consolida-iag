package br.com.consolidaiag.catalog.domain.exceptions;

import br.com.consolidaiag.shared.domain.exceptions.BusinessRuleException;

public final class InvalidProductDataException extends BusinessRuleException {

    public InvalidProductDataException(String message) {
        super(message);
    }
}