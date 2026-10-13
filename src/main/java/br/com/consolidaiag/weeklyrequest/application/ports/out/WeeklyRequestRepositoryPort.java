package br.com.consolidaiag.weeklyrequest.application.ports.out;

import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;
import br.com.consolidaiag.weeklyrequest.domain.WeeklyRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WeeklyRequestRepositoryPort {
    boolean existsByServiceCodeAndCycleId(ServiceCode code, UUID cycleId);

    WeeklyRequest save(WeeklyRequest request);

    Optional<WeeklyRequest> findById(UUID id);

    Optional<WeeklyRequest> findByServiceCodeAndCycleId(ServiceCode code, UUID cycleId);

    List<WeeklyRequest> findAllByCycleId(UUID cycleId);
}