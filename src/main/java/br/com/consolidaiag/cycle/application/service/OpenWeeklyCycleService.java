package br.com.consolidaiag.cycle.application.service;

import br.com.consolidaiag.cycle.application.dtos.request.OpenWeeklyCyclePayload;
import br.com.consolidaiag.cycle.application.dtos.response.WeeklyCycleResponse;
import br.com.consolidaiag.cycle.application.policies.OpenCyclePolicy;
import br.com.consolidaiag.cycle.application.ports.in.OpenWeeklyCycleUseCase;
import br.com.consolidaiag.cycle.application.ports.out.WeeklyCycleRepositoryPort;
import br.com.consolidaiag.cycle.domain.WeeklyCycle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OpenWeeklyCycleService implements OpenWeeklyCycleUseCase {

    private final WeeklyCycleRepositoryPort repository;
    private final List<OpenCyclePolicy> policies;

    public OpenWeeklyCycleService(WeeklyCycleRepositoryPort repository, List<OpenCyclePolicy> policies) {
        this.repository = repository;
        this.policies = policies != null ? List.copyOf(policies) : List.of();
    }

    @Override
    @Transactional
    public WeeklyCycleResponse execute(OpenWeeklyCyclePayload payload) {
        WeeklyCycle cycle = WeeklyCycle.openNew(payload.startDate(), payload.endDate());

        policies.forEach(policy -> policy.validate(cycle));

        WeeklyCycle saved = repository.save(cycle);

        return new WeeklyCycleResponse(
                saved.getId().value(),
                saved.getStartDate(),
                saved.getEndDate(),
                saved.getStatus()
        );
    }
}