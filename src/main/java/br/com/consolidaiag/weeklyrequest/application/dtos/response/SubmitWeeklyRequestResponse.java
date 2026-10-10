package br.com.consolidaiag.weeklyrequest.application.dtos.response;

import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;

import java.time.LocalDateTime;

public record SubmitWeeklyRequestResponse(
        Long id,
        ServiceCode serviceCode,
        Long cycleId,
        LocalDateTime createdAt
) {
}