package br.com.consolidaiag.weeklyrequest.application.ports.out;

import java.util.Optional;

public interface ActiveCyclePort {
    Optional<Long> getActiveCycleId();
}
