package br.com.consolidaiag.weeklycycle.application.ports.in;

import br.com.consolidaiag.weeklycycle.application.dtos.request.OpenWeeklyCyclePayload;
import br.com.consolidaiag.weeklycycle.application.dtos.response.WeeklyCycleResponse;

public interface OpenWeeklyCycleUseCase {
    WeeklyCycleResponse execute(OpenWeeklyCyclePayload payload);
}