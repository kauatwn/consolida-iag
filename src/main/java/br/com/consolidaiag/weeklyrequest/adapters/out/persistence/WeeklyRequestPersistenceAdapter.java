package br.com.consolidaiag.weeklyrequest.adapters.out.persistence;

import br.com.consolidaiag.weeklyrequest.application.ports.out.WeeklyRequestRepositoryPort;
import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;
import br.com.consolidaiag.weeklyrequest.domain.WeeklyRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class WeeklyRequestPersistenceAdapter implements WeeklyRequestRepositoryPort {

    private final WeeklyRequestJpaRepository jpaRepository;
    private final WeeklyRequestPersistenceMapper mapper;

    public WeeklyRequestPersistenceAdapter(WeeklyRequestJpaRepository jpaRepository, WeeklyRequestPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean existsByServiceCodeAndCycleId(ServiceCode serviceCode, UUID cycleId) {
        return jpaRepository.existsByServiceCodeAndCycleId(serviceCode, cycleId);
    }

    @Override
    public WeeklyRequest save(WeeklyRequest request) {
        WeeklyRequestJpaEntity entity = jpaRepository.findById(request.getId().value())
                .map(existing -> {
                    mapper.updateJpaEntity(request, existing);

                    return existing;
                })
                .orElseGet(() -> mapper.toJpaEntity(request));

        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<WeeklyRequest> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<WeeklyRequest> findByServiceCodeAndCycleId(ServiceCode serviceCode, UUID cycleId) {
        return jpaRepository.findByServiceCodeAndCycleId(serviceCode, cycleId).map(mapper::toDomain);
    }

    @Override
    public List<WeeklyRequest> findAllByCycleId(UUID cycleId) {
        return jpaRepository.findAllByCycleId(cycleId).stream()
                .map(mapper::toDomain)
                .toList();
    }
}