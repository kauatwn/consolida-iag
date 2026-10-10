package br.com.consolidaiag.weeklyrequest.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public record RequestItem(
        UUID productId,
        BigDecimal quantity
) {

    public RequestItem {
        Objects.requireNonNull(productId, "Product ID cannot be null.");
        Objects.requireNonNull(quantity, "Quantity cannot be null.");

        // Enforcing RN-02.2 inside the domain
        if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
    }
}