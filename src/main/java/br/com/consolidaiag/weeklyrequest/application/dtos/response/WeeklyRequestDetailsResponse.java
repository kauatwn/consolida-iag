package br.com.consolidaiag.weeklyrequest.application.dtos.response;

import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record WeeklyRequestDetailsResponse(
        UUID id,
        ServiceCode serviceCode,
        UUID cycleId,
        String observation,
        LocalDateTime createdAt,
        List<RequestItemDetailsResponse> items
) {
}