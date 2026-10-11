package br.com.consolidaiag.cycle.domain;

import java.util.Objects;
import java.util.UUID;

public record WeeklyCycleId(UUID value) {
    public WeeklyCycleId {
        Objects.requireNonNull(value, "WeeklyCycleId value cannot be null.");
    }

    public static WeeklyCycleId generate() {
        return new WeeklyCycleId(UUID.randomUUID());
    }
}