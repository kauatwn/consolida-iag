package br.com.consolidaiag.weeklyrequest.domain.exceptions;

import br.com.consolidaiag.shared.DomainException;

public final class EmptyRequestItemsException extends DomainException {
    public EmptyRequestItemsException() {
        super("A weekly request must contain at least one item (RN-02.2).");
    }
}