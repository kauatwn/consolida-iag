package br.com.consolidaiag.catalog.application.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Payload utilizado para solicitar a desativação de um produto no catálogo.
 */
@Schema(description = "Payload para desativação de um produto no catálogo")
public record DeactivateProductPayload(

        @NotNull(message = "O identificador do produto é obrigatório")
        @Schema(
                description = "Identificador único (UUID) do produto que será desativado",
                example = "123e4567-e89b-12d3-a456-426614174000",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        UUID productId
) {
}