package br.com.consolidaiag.weeklycycle.application.ports.in;

import br.com.consolidaiag.weeklycycle.application.dtos.response.WeeklyCycleResponse;

import java.util.UUID;

public interface CloseWeeklyCycleUseCase {
    WeeklyCycleResponse execute(UUID cycleId);
}