package br.com.consolidaiag.weeklyrequest.domain.exceptions;

import br.com.consolidaiag.shared.domain.exceptions.BusinessRuleException;

import java.math.BigDecimal;

public final class InvalidQuantityException extends BusinessRuleException {

    private final BigDecimal quantity;

    public InvalidQuantityException(BigDecimal quantity) {
        super(String.format("The requested quantity must be greater than zero. Provided: %s (RN-02.2).", quantity));
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
}