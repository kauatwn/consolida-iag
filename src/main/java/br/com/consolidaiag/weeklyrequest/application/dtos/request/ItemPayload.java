package br.com.consolidaiag.weeklyrequest.application.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemPayload(
        @NotNull(message = "O identificador do produto é obrigatório")
        UUID productId,

        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade deve ser estritamente maior que zero")
        BigDecimal quantity
) {
}