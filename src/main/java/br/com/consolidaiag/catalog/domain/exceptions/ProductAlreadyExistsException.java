package br.com.consolidaiag.catalog.domain.exceptions;

import br.com.consolidaiag.shared.DomainException;

public final class ProductAlreadyExistsException extends DomainException {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
