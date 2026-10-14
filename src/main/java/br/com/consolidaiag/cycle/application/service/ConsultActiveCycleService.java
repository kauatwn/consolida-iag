package br.com.consolidaiag.cycle.application.service;

import br.com.consolidaiag.cycle.application.dtos.response.WeeklyCycleResponse;
import br.com.consolidaiag.cycle.application.ports.in.ConsultActiveCycleUseCase;
import br.com.consolidaiag.cycle.application.ports.out.WeeklyCycleRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ConsultActiveCycleService implements ConsultActiveCycleUseCase {

    private final WeeklyCycleRepositoryPort repository;

    public ConsultActiveCycleService(WeeklyCycleRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WeeklyCycleResponse> execute() {
        return repository.findActiveCycle()
                .map(cycle -> new WeeklyCycleResponse(
                        cycle.getId().value(),
                        cycle.getStartDate(),
                        cycle.getEndDate(),
                        cycle.getStatus()
                ));
    }
}