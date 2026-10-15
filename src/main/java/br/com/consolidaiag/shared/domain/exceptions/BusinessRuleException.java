package br.com.consolidaiag.shared.domain.exceptions;

public abstract class BusinessRuleException extends DomainException {

    protected BusinessRuleException(String message) {
        super(message);
    }
}