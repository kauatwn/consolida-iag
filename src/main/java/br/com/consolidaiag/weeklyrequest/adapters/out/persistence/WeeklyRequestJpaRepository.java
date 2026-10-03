package br.com.consolidaiag.weeklyrequest.adapters.out.persistence;

import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyRequestJpaRepository extends JpaRepository<WeeklyRequestJpaEntity, Long> {
    boolean existsByServiceCodeAndCycleId(ServiceCode serviceCode, Long cycleId);
}