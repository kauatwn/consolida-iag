package br.com.consolidaiag.weeklycycle.application.ports.out;

import br.com.consolidaiag.weeklycycle.domain.WeeklyCycle;

import java.util.Optional;
import java.util.UUID;

public interface WeeklyCycleRepositoryPort {
    boolean hasActiveCycle();

    WeeklyCycle save(WeeklyCycle cycle);

    Optional<WeeklyCycle> findById(UUID id);

    Optional<WeeklyCycle> findActiveCycle();
}