package br.com.consolidaiag.weeklyrequest.domain.exceptions;

import br.com.consolidaiag.shared.domain.exceptions.ConflictException;

import java.util.UUID;

public final class DuplicateRequestItemException extends ConflictException {

    public DuplicateRequestItemException(UUID productId) {
        super("Product with ID " + productId + " is already added to this request.");
    }
}