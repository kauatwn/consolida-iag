package br.com.consolidaiag.weeklyrequest.application.service;

import br.com.consolidaiag.weeklyrequest.application.dtos.response.RequestItemDetailsResponse;
import br.com.consolidaiag.weeklyrequest.application.dtos.response.WeeklyRequestDetailsResponse;
import br.com.consolidaiag.weeklyrequest.application.ports.in.ConsultActiveRequestUseCase;
import br.com.consolidaiag.weeklyrequest.application.ports.out.FetchActiveCyclePort;
import br.com.consolidaiag.weeklyrequest.application.ports.out.WeeklyRequestRepositoryPort;
import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ConsultActiveRequestService implements ConsultActiveRequestUseCase {

    private static final Logger log = LoggerFactory.getLogger(ConsultActiveRequestService.class);

    private final WeeklyRequestRepositoryPort repository;
    private final FetchActiveCyclePort cyclePort;

    public ConsultActiveRequestService(WeeklyRequestRepositoryPort repository, FetchActiveCyclePort cyclePort) {
        this.repository = repository;
        this.cyclePort = cyclePort;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WeeklyRequestDetailsResponse> execute(ServiceCode serviceCode) {
        log.info("Iniciando consulta de requisição ativa para o código de serviço: {}", serviceCode);

        Optional<WeeklyRequestDetailsResponse> response = cyclePort.getActiveCycleId()
                .map(cycleId -> {
                    log.debug("Ciclo ativo encontrado com ID: {}. Buscando requisição...", cycleId);
                    return cycleId;
                })
                .flatMap(cycleId -> repository.findByServiceCodeAndCycleId(serviceCode, cycleId))
                .map(request -> {
                    log.info("Requisição ativa encontrada com ID: {} para o ciclo: {}", request.getId().value(), request.getCycleId());
                    return new WeeklyRequestDetailsResponse(
                            request.getId().value(),
                            request.getServiceCode(),
                            request.getCycleId(),
                            request.getObservation(),
                            request.getCreatedAt(),
                            request.getItems().stream()
                                    .map(item -> new RequestItemDetailsResponse(item.productId(), item.quantity()))
                                    .toList()
                    );
                });

        if (response.isEmpty()) {
            log.warn("Nenhuma requisição ativa foi encontrada para o código de serviço: {} (ou não há ciclo ativo).", serviceCode);
        }

        return response;
    }
}
