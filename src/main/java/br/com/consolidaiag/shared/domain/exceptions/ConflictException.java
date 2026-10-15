package br.com.consolidaiag.shared.domain.exceptions;

public abstract class ConflictException extends DomainException {

    protected ConflictException(String message) {
        super(message);
    }
}