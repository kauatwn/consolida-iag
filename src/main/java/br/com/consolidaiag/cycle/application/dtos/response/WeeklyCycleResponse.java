package br.com.consolidaiag.cycle.application.dtos.response;

import br.com.consolidaiag.cycle.domain.CycleStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO de resposta contendo os detalhes de um ciclo semanal.
 */
@Schema(description = "Detalhes e status de um ciclo semanal no sistema")
public record WeeklyCycleResponse(

        @Schema(
                description = "Identificador único (UUID) do ciclo semanal",
                example = "123e4567-e89b-12d3-a456-426614174000"
        )
        UUID id,

        @Schema(
                description = "Data de início da vigência do ciclo semanal",
                example = "2026-11-02"
        )
        LocalDate startDate,

        @Schema(
                description = "Data de término da vigência do ciclo semanal",
                example = "2026-11-08"
        )
        LocalDate endDate,

        @Schema(
                description = "Status atual do ciclo semanal (ex: OPEN, CLOSED)",
                example = "OPEN"
        )
        CycleStatus status
) {
}