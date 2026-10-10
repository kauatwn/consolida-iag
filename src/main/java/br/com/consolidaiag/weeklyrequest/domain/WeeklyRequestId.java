package br.com.consolidaiag.weeklyrequest.domain;

import java.util.Objects;
import java.util.UUID;

public record WeeklyRequestId(UUID value) {

    public WeeklyRequestId {
        Objects.requireNonNull(value, "WeeklyRequestId value cannot be null.");
    }

    public static WeeklyRequestId generate() {
        return new WeeklyRequestId(UUID.randomUUID());
    }
}