package br.com.consolidaiag.catalog.domain.exceptions;

import br.com.consolidaiag.shared.domain.exceptions.ConflictException;

public final class ProductAlreadyExistsException extends ConflictException {

    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}