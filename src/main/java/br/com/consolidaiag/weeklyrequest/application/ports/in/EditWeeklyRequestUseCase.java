package br.com.consolidaiag.weeklyrequest.application.ports.in;

import br.com.consolidaiag.weeklyrequest.application.dtos.request.EditWeeklyRequestPayload;
import br.com.consolidaiag.weeklyrequest.application.dtos.response.SubmitWeeklyRequestResponse;

import java.util.UUID;

public interface EditWeeklyRequestUseCase {
    SubmitWeeklyRequestResponse execute(UUID requestId, EditWeeklyRequestPayload payload);
}