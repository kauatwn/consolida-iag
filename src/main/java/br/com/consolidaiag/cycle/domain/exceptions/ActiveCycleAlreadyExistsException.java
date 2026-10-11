package br.com.consolidaiag.cycle.domain.exceptions;

public class ActiveCycleAlreadyExistsException extends RuntimeException {

    public ActiveCycleAlreadyExistsException() {
        super("Cannot open a new purchasing cycle while another cycle is still open (RN-01).");
    }

    public ActiveCycleAlreadyExistsException(String message) {
        super(message);
    }
}