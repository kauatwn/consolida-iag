package br.com.consolidaiag.cycle.application.dtos.response;

import br.com.consolidaiag.cycle.domain.CycleStatus;

import java.time.LocalDate;
import java.util.UUID;

public record WeeklyCycleResponse(
        UUID id,
        LocalDate startDate,
        LocalDate endDate,
        CycleStatus status
) {
}