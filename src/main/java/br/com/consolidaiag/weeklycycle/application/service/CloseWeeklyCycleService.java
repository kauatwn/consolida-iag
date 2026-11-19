package br.com.consolidaiag.weeklycycle.application.service;

import br.com.consolidaiag.weeklycycle.application.dtos.response.WeeklyCycleResponse;
import br.com.consolidaiag.weeklycycle.application.ports.in.CloseWeeklyCycleUseCase;
import br.com.consolidaiag.weeklycycle.application.ports.out.WeeklyCycleRepositoryPort;
import br.com.consolidaiag.weeklycycle.domain.WeeklyCycle;
import br.com.consolidaiag.weeklycycle.domain.exceptions.WeeklyCycleNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CloseWeeklyCycleService implements CloseWeeklyCycleUseCase {

    private static final Logger log = LoggerFactory.getLogger(CloseWeeklyCycleService.class);

    private final WeeklyCycleRepositoryPort repository;

    public CloseWeeklyCycleService(WeeklyCycleRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public WeeklyCycleResponse execute(UUID cycleId) {
        log.info("Iniciando fechamento do ciclo semanal com ID: {}", cycleId);

        WeeklyCycle cycle = repository.findById(cycleId)
                .orElseGet(() -> {
                    log.warn("Ciclo semanal com ID {} não encontrado para fechamento.", cycleId);
                    throw new WeeklyCycleNotFoundException(cycleId);
                });

        cycle.close();

        WeeklyCycle saved = repository.save(cycle);

        log.info("Ciclo semanal com ID {} fechado com sucesso.", cycleId);

        return new WeeklyCycleResponse(
                saved.getId().value(),
                saved.getStartDate(),
                saved.getEndDate(),
                saved.getStatus()
        );
    }
}
