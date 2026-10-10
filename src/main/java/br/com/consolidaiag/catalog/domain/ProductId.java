package br.com.consolidaiag.catalog.domain;

import java.util.Objects;
import java.util.UUID;

public record ProductId(UUID value) {

    public ProductId {
        Objects.requireNonNull(value, "ProductId value cannot be null.");
    }

    public static ProductId generate() {
        return new ProductId(UUID.randomUUID());
    }
}