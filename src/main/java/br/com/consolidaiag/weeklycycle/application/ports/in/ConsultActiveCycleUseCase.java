package br.com.consolidaiag.weeklycycle.application.ports.in;

import br.com.consolidaiag.weeklycycle.application.dtos.response.WeeklyCycleResponse;

import java.util.Optional;

public interface ConsultActiveCycleUseCase {
    Optional<WeeklyCycleResponse> execute();
}