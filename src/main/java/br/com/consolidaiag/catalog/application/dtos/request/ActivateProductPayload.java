package br.com.consolidaiag.catalog.application.dtos.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ActivateProductPayload(
        @NotNull(message = "O identificador do produto é obrigatório")
        UUID productId
) {
}
