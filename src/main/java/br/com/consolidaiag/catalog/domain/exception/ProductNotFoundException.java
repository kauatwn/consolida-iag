package br.com.consolidaiag.catalog.domain.exception;

import br.com.consolidaiag.shared.DomainException;

public final class ProductNotFoundException extends DomainException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
