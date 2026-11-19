package br.com.consolidaiag.shared.domain.exceptions;

public abstract class ForbiddenException extends DomainException {

    public ForbiddenException(String message) {
        super(message);
    }
}
