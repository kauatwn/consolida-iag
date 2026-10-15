package br.com.consolidaiag.shared.domain.exceptions;

public abstract class ResourceNotFoundException extends DomainException {

    protected ResourceNotFoundException(String message) {
        super(message);
    }
}