package br.com.consolidaiag.weeklyrequest.application.ports.in;

import br.com.consolidaiag.weeklyrequest.application.dtos.request.SubmitWeeklyRequestPayload;
import br.com.consolidaiag.weeklyrequest.application.dtos.response.SubmitWeeklyRequestResponse;

public interface SubmitWeeklyRequestUseCase {
    SubmitWeeklyRequestResponse execute(SubmitWeeklyRequestPayload payload);
}