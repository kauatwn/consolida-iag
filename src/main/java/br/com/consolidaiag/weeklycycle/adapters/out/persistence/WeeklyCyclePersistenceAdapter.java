package br.com.consolidaiag.weeklycycle.adapters.out.persistence;

import br.com.consolidaiag.weeklycycle.application.ports.out.WeeklyCycleRepositoryPort;
import br.com.consolidaiag.weeklycycle.domain.CycleStatus;
import br.com.consolidaiag.weeklycycle.domain.WeeklyCycle;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class WeeklyCyclePersistenceAdapter implements WeeklyCycleRepositoryPort {

    private final WeeklyCycleJpaRepository jpaRepository;
    private final WeeklyCyclePersistenceMapper mapper;

    public WeeklyCyclePersistenceAdapter(WeeklyCycleJpaRepository jpaRepository, WeeklyCyclePersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean hasActiveCycle() {
        return jpaRepository.existsByStatus(CycleStatus.OPEN);
    }

    @Override
    public WeeklyCycle save(WeeklyCycle cycle) {
        WeeklyCycleJpaEntity entity = mapper.toJpaEntity(cycle);
        WeeklyCycleJpaEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<WeeklyCycle> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<WeeklyCycle> findActiveCycle() {
        return jpaRepository.findByStatus(CycleStatus.OPEN).map(mapper::toDomain);
    }
}