package br.com.consolidaiag.weeklyrequest.domain.exceptions;

import java.util.UUID;

public final class DuplicateRequestItemException extends RuntimeException {
    public DuplicateRequestItemException(UUID productId) {
        super("Product with ID " + productId + " is already added to this request.");
    }
}