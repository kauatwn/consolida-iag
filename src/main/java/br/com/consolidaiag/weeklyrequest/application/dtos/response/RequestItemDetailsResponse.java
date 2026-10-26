package br.com.consolidaiag.weeklyrequest.application.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO de resposta que representa os detalhes de um item pertencente a uma requisição semanal.
 */
@Schema(description = "Detalhes de um item contido na requisição semanal")
public record RequestItemDetailsResponse(

        @Schema(
                description = "Identificador único (UUID) do produto associado ao item",
                example = "123e4567-e89b-12d3-a456-426614174000"
        )
        UUID productId,

        @Schema(
                description = "Quantidade solicitada do produto",
                example = "15.00"
        )
        BigDecimal quantity
) {
}