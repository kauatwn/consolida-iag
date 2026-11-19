package br.com.consolidaiag.weeklycycle.application.service;

import br.com.consolidaiag.weeklycycle.application.dtos.request.OpenWeeklyCyclePayload;
import br.com.consolidaiag.weeklycycle.application.dtos.response.WeeklyCycleResponse;
import br.com.consolidaiag.weeklycycle.application.policies.OpenWeeklyCyclePolicy;
import br.com.consolidaiag.weeklycycle.application.ports.in.OpenWeeklyCycleUseCase;
import br.com.consolidaiag.weeklycycle.application.ports.out.WeeklyCycleRepositoryPort;
import br.com.consolidaiag.weeklycycle.domain.WeeklyCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OpenWeeklyCycleService implements OpenWeeklyCycleUseCase {

    private static final Logger log = LoggerFactory.getLogger(OpenWeeklyCycleService.class);

    private final WeeklyCycleRepositoryPort repository;
    private final List<OpenWeeklyCyclePolicy> policies;

    public OpenWeeklyCycleService(WeeklyCycleRepositoryPort repository, List<OpenWeeklyCyclePolicy> policies) {
        this.repository = repository;
        this.policies = policies != null ? List.copyOf(policies) : List.of();
    }

    @Override
    @Transactional
    public WeeklyCycleResponse execute(OpenWeeklyCyclePayload payload) {
        log.info("Iniciando abertura de novo ciclo semanal. Período: {} a {}", payload.startDate(), payload.endDate());

        WeeklyCycle cycle = WeeklyCycle.openNew(payload.startDate(), payload.endDate());

        log.debug("Aplicando {} políticas de validação para a abertura do ciclo...", policies.size());
        policies.forEach(policy -> policy.validate(cycle));

        WeeklyCycle saved = repository.save(cycle);

        log.info("Ciclo semanal aberto e salvo com sucesso com o ID: {}", saved.getId().value());

        return new WeeklyCycleResponse(
                saved.getId().value(),
                saved.getStartDate(),
                saved.getEndDate(),
                saved.getStatus()
        );
    }
}
