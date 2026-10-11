package br.com.consolidaiag.cycle.application.dtos.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record OpenWeeklyCyclePayload(
        @NotNull(message = "A data de início é obrigatória")
        LocalDate startDate,

        @NotNull(message = "A data de término é obrigatória")
        LocalDate endDate
) {
}