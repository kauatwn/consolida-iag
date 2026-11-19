package br.com.consolidaiag.weeklycycle.application.service;

import br.com.consolidaiag.weeklycycle.application.dtos.response.WeeklyCycleResponse;
import br.com.consolidaiag.weeklycycle.application.ports.in.ConsultActiveCycleUseCase;
import br.com.consolidaiag.weeklycycle.application.ports.out.WeeklyCycleRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ConsultActiveCycleService implements ConsultActiveCycleUseCase {

    private static final Logger log = LoggerFactory.getLogger(ConsultActiveCycleService.class);

    private final WeeklyCycleRepositoryPort repository;

    public ConsultActiveCycleService(WeeklyCycleRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WeeklyCycleResponse> execute() {
        log.info("Iniciando consulta pelo ciclo semanal ativo.");

        Optional<WeeklyCycleResponse> activeCycleResponse = repository.findActiveCycle()
                .map(cycle -> {
                    log.info("Ciclo semanal ativo encontrado com ID: {}", cycle.getId().value());
                    return new WeeklyCycleResponse(
                            cycle.getId().value(),
                            cycle.getStartDate(),
                            cycle.getEndDate(),
                            cycle.getStatus()
                    );
                });

        if (activeCycleResponse.isEmpty()) {
            log.warn("Nenhum ciclo semanal ativo encontrado no momento.");
        }

        return activeCycleResponse;
    }
}
