package br.com.consolidaiag.weeklycycle.application.service;

import br.com.consolidaiag.weeklycycle.application.ports.in.FetchActiveCycleUseCase;
import br.com.consolidaiag.weeklycycle.application.ports.out.WeeklyCycleRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class FetchActiveCycleService implements FetchActiveCycleUseCase {

    private static final Logger log = LoggerFactory.getLogger(FetchActiveCycleService.class);

    private final WeeklyCycleRepositoryPort repository;

    public FetchActiveCycleService(WeeklyCycleRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UUID> execute() {
        log.info("Iniciando busca pelo ID do ciclo semanal ativo.");

        Optional<UUID> activeCycleId = repository.findActiveCycle()
                .map(cycle -> {
                    UUID id = cycle.getId().value();
                    log.info("Ciclo semanal ativo encontrado com ID: {}", id);
                    return id;
                });

        if (activeCycleId.isEmpty()) {
            log.warn("Nenhum ID de ciclo semanal ativo foi encontrado.");
        }

        return activeCycleId;
    }
}
