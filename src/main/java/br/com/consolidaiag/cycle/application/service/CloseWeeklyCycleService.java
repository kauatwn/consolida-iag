package br.com.consolidaiag.cycle.application.service;

import br.com.consolidaiag.cycle.application.dtos.response.WeeklyCycleResponse;
import br.com.consolidaiag.cycle.application.ports.in.CloseWeeklyCycleUseCase;
import br.com.consolidaiag.cycle.application.ports.out.WeeklyCycleRepositoryPort;
import br.com.consolidaiag.cycle.domain.WeeklyCycle;
import br.com.consolidaiag.cycle.domain.exceptions.WeeklyCycleNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CloseWeeklyCycleService implements CloseWeeklyCycleUseCase {

    private final WeeklyCycleRepositoryPort repository;

    public CloseWeeklyCycleService(WeeklyCycleRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public WeeklyCycleResponse execute(UUID cycleId) {
        WeeklyCycle cycle = repository.findById(cycleId)
                .orElseThrow(() -> new WeeklyCycleNotFoundException(cycleId));

        cycle.close();

        WeeklyCycle saved = repository.save(cycle);

        return new WeeklyCycleResponse(
                saved.getId().value(),
                saved.getStartDate(),
                saved.getEndDate(),
                saved.getStatus()
        );
    }
}