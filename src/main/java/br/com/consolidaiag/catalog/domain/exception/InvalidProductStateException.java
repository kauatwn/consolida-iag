package br.com.consolidaiag.catalog.domain.exception;

import br.com.consolidaiag.shared.DomainException;

public final class InvalidProductStateException extends DomainException {
    public InvalidProductStateException(String message) {
        super(message);
    }
}
