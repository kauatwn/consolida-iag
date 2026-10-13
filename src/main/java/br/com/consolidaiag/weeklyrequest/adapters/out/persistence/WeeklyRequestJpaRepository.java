package br.com.consolidaiag.weeklyrequest.adapters.out.persistence;

import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WeeklyRequestJpaRepository extends JpaRepository<WeeklyRequestJpaEntity, UUID> {
    boolean existsByServiceCodeAndCycleId(ServiceCode serviceCode, UUID cycleId);

    Optional<WeeklyRequestJpaEntity> findByServiceCodeAndCycleId(ServiceCode serviceCode, UUID cycleId);

    List<WeeklyRequestJpaEntity> findAllByCycleId(UUID cycleId);
}