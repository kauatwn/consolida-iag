package br.com.consolidaiag.catalog.application.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Payload utilizado para solicitar a ativação de um produto no catálogo.
 */
@Schema(description = "Payload para ativação de um produto no catálogo")
public record ActivateProductPayload(

        @NotNull(message = "O identificador do produto é obrigatório")
        @Schema(
                description = "Identificador único (UUID) do produto que será ativado",
                example = "123e4567-e89b-12d3-a456-426614174000",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        UUID productId
) {
}