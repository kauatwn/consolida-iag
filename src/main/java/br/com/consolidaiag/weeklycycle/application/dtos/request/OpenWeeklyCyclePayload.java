package br.com.consolidaiag.weeklycycle.application.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Payload utilizado para solicitar a abertura de um novo ciclo semanal.
 */
@Schema(description = "Payload para abertura de um novo ciclo semanal informando o período de vigência")
public record OpenWeeklyCyclePayload(

        @NotNull(message = "A data de início é obrigatória")
        @Schema(
                description = "Data de início da vigência do ciclo semanal (formato ISO-8601)",
                example = "2026-11-02",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        LocalDate startDate,

        @NotNull(message = "A data de término é obrigatória")
        @Schema(
                description = "Data de término da vigência do ciclo semanal (formato ISO-8601)",
                example = "2026-11-08",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        LocalDate endDate
) {
}