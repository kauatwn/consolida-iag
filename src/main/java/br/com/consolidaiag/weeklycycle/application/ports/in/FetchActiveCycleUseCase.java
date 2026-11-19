package br.com.consolidaiag.weeklycycle.application.ports.in;

import java.util.Optional;
import java.util.UUID;

public interface FetchActiveCycleUseCase {
    Optional<UUID> execute();
}