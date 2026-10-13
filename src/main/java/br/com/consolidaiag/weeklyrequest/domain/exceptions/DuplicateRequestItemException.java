package br.com.consolidaiag.weeklyrequest.domain.exceptions;

import br.com.consolidaiag.shared.DomainException;

import java.util.UUID;

public final class DuplicateRequestItemException extends DomainException {
    public DuplicateRequestItemException(UUID productId) {
        super("Product with ID " + productId + " is already added to this request.");
    }
}