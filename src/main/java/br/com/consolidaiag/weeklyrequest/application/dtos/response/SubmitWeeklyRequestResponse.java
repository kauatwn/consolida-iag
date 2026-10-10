package br.com.consolidaiag.weeklyrequest.application.dtos.response;

import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;

import java.time.LocalDateTime;
import java.util.UUID;

public record SubmitWeeklyRequestResponse(
        UUID id,
        ServiceCode serviceCode,
        UUID cycleId,
        LocalDateTime createdAt
) {
}