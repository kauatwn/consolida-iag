package br.com.consolidaiag.weeklyrequest.application.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Payload que representa um item individual dentro de uma requisição semanal.
 */
@Schema(description = "Representa um item contido na solicitação de requisição semanal")
public record ItemPayload(

        @NotNull(message = "O identificador do produto é obrigatório")
        @Schema(
                description = "Identificador único (UUID) do produto cadastrado no catálogo",
                example = "123e4567-e89b-12d3-a456-426614174000",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        UUID productId,

        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade deve ser estritamente maior que zero")
        @Schema(
                description = "Quantidade solicitada do produto (deve ser maior que zero)",
                example = "10.5",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        BigDecimal quantity
) {
}