package br.com.consolidaiag.cycle.application.service;

import br.com.consolidaiag.cycle.application.ports.in.FetchActiveCycleUseCase;
import br.com.consolidaiag.cycle.application.ports.out.WeeklyCycleRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FetchActiveCycleService implements FetchActiveCycleUseCase {

    private final WeeklyCycleRepositoryPort repository;

    public FetchActiveCycleService(WeeklyCycleRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Optional<UUID> execute() {
        return repository.findActiveCycle()
                .map(cycle -> cycle.getId().value());
    }
}