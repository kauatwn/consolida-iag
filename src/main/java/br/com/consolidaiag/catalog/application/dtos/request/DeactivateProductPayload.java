package br.com.consolidaiag.catalog.application.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeactivateProductPayload(
        @NotBlank(message = "O ID do produto é obrigatório")
        @NotNull(message = "O ID do produto não pode ser nulo")
        UUID productId
) {
}
