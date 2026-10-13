package br.com.consolidaiag.cycle.application.ports.in;

import br.com.consolidaiag.cycle.application.dtos.response.WeeklyCycleResponse;

import java.util.Optional;

public interface ConsultActiveCycleUseCase {
    Optional<WeeklyCycleResponse> execute();
}