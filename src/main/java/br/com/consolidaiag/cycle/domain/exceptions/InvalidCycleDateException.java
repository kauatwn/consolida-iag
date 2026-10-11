package br.com.consolidaiag.cycle.domain.exceptions;

public final class InvalidCycleDateException extends RuntimeException {

    public InvalidCycleDateException() {
        super("The cycle end date cannot be before the start date.");
    }

    public InvalidCycleDateException(String message) {
        super(message);
    }
}