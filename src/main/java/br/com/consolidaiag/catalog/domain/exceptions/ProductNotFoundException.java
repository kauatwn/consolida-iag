package br.com.consolidaiag.catalog.domain.exceptions;

import br.com.consolidaiag.shared.domain.exceptions.ResourceNotFoundException;

import java.util.UUID;

public final class ProductNotFoundException extends ResourceNotFoundException {

    public ProductNotFoundException(UUID productId) {
        super("Product not found with ID: " + productId);
    }
}