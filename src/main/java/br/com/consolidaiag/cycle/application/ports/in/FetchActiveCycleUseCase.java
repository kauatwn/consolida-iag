package br.com.consolidaiag.cycle.application.ports.in;

import java.util.Optional;
import java.util.UUID;

public interface FetchActiveCycleUseCase {
    Optional<UUID> execute();
}