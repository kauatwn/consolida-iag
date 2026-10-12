package br.com.consolidaiag.catalog.domain.exception;

import br.com.consolidaiag.shared.DomainException;

public final class InvalidProductDataException extends DomainException {
    public InvalidProductDataException(String message) {
        super(message);
    }
}
