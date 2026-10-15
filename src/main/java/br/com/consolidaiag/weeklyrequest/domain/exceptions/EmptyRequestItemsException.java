package br.com.consolidaiag.weeklyrequest.domain.exceptions;

import br.com.consolidaiag.shared.domain.exceptions.BusinessRuleException;

public final class EmptyRequestItemsException extends BusinessRuleException {

    public EmptyRequestItemsException() {
        super("A weekly request must contain at least one item (RN-02.2).");
    }
}