package br.com.consolidaiag.catalog.domain.exceptions;

import br.com.consolidaiag.shared.DomainException;

import java.util.UUID;

public final class ProductNotFoundException extends DomainException {

    public ProductNotFoundException(UUID productId) {
        super("Product not found with ID: " + productId);
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
