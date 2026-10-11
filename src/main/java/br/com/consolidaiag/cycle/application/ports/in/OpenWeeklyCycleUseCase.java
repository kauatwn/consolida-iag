package br.com.consolidaiag.cycle.application.ports.in;

import br.com.consolidaiag.cycle.application.dtos.request.OpenWeeklyCyclePayload;
import br.com.consolidaiag.cycle.application.dtos.response.WeeklyCycleResponse;

public interface OpenWeeklyCycleUseCase {
    WeeklyCycleResponse execute(OpenWeeklyCyclePayload payload);
}