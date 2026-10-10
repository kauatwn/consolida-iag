package br.com.consolidaiag.weeklyrequest.application.ports.out;

import java.util.Optional;
import java.util.UUID;

public interface ActiveCyclePort {
    Optional<UUID> getActiveCycleId();
}