package br.com.consolidaiag.weeklyrequest.domain.exceptions;

public final class EmptyRequestItemsException extends RuntimeException {
    public EmptyRequestItemsException() {
        super("A weekly request must contain at least one item (RN-02.2).");
    }
}