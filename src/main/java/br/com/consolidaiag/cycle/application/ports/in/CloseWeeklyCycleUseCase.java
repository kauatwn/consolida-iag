package br.com.consolidaiag.cycle.application.ports.in;

import br.com.consolidaiag.cycle.application.dtos.response.WeeklyCycleResponse;

import java.util.UUID;

public interface CloseWeeklyCycleUseCase {
    WeeklyCycleResponse execute(UUID cycleId);
}