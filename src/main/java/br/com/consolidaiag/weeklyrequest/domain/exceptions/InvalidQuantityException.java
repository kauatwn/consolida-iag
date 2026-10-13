package br.com.consolidaiag.weeklyrequest.domain.exceptions;

import java.math.BigDecimal;

public final class InvalidQuantityException extends RuntimeException {

    private final BigDecimal quantity;

    public InvalidQuantityException(BigDecimal quantity) {
        super(String.format("The requested quantity must be greater than zero. Provided: %s (RN-02.2).", quantity));
        this.quantity = quantity;
    }

    public InvalidQuantityException(String message) {
        super(message);
        this.quantity = null;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
}