package br.com.consolidaiag.user.domain.exceptions;

import br.com.consolidaiag.shared.domain.exceptions.BusinessRuleException;

public final class InvalidUserDataException extends BusinessRuleException {

    public InvalidUserDataException(String message) {
        super(message);
    }
}
