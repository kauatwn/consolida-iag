package br.com.consolidaiag.weeklyrequest.application.ports.out;

import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;
import br.com.consolidaiag.weeklyrequest.domain.WeeklyRequest;
import java.util.Optional;

public interface WeeklyRequestRepositoryPort {
    boolean existsByServiceCodeAndCycleId(ServiceCode code, Long cycleId);
    WeeklyRequest save(WeeklyRequest request);
    Optional<WeeklyRequest> findById(Long id);
}