package br.com.consolidaiag.cycle.domain.exceptions;

public final class CycleAlreadyClosedException extends RuntimeException {

    public CycleAlreadyClosedException() {
        super("This cycle is already closed.");
    }

    public CycleAlreadyClosedException(String message) {
        super(message);
    }
}