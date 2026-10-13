package br.com.consolidaiag.weeklyrequest.application.ports.in;

import br.com.consolidaiag.weeklyrequest.application.dtos.response.WeeklyRequestDetailsResponse;

import java.util.List;
import java.util.UUID;

public interface ReviewCycleRequestsUseCase {
    List<WeeklyRequestDetailsResponse> execute(UUID cycleId);
}