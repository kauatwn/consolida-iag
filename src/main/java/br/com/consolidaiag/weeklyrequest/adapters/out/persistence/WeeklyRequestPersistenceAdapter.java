package br.com.consolidaiag.weeklyrequest.adapters.out.persistence;

import br.com.consolidaiag.weeklyrequest.application.ports.out.WeeklyRequestRepositoryPort;
import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;
import br.com.consolidaiag.weeklyrequest.domain.WeeklyRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WeeklyRequestPersistenceAdapter implements WeeklyRequestRepositoryPort {

    private final WeeklyRequestJpaRepository jpaRepository;
    private final WeeklyRequestPersistenceMapper mapper;

    public WeeklyRequestPersistenceAdapter(WeeklyRequestJpaRepository jpaRepository, WeeklyRequestPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean existsByServiceCodeAndCycleId(ServiceCode serviceCode, Long cycleId) {
        return jpaRepository.existsByServiceCodeAndCycleId(serviceCode, cycleId);
    }

    @Override
    public WeeklyRequest save(WeeklyRequest weeklyRequest) {
        WeeklyRequestJpaEntity jpaEntity = mapper.toJpaEntity(weeklyRequest);
        WeeklyRequestJpaEntity savedJpaEntity = jpaRepository.save(jpaEntity);
        return mapper.toDomain(savedJpaEntity);
    }

    @Override
    public Optional<WeeklyRequest> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
}