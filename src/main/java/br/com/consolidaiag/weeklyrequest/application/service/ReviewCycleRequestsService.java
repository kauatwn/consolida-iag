package br.com.consolidaiag.weeklyrequest.application.service;

import br.com.consolidaiag.weeklyrequest.application.dtos.response.RequestItemDetailsResponse;
import br.com.consolidaiag.weeklyrequest.application.dtos.response.WeeklyRequestDetailsResponse;
import br.com.consolidaiag.weeklyrequest.application.ports.in.ReviewCycleRequestsUseCase;
import br.com.consolidaiag.weeklyrequest.application.ports.out.WeeklyRequestRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewCycleRequestsService implements ReviewCycleRequestsUseCase {

    private static final Logger log = LoggerFactory.getLogger(ReviewCycleRequestsService.class);

    private final WeeklyRequestRepositoryPort repository;

    public ReviewCycleRequestsService(WeeklyRequestRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WeeklyRequestDetailsResponse> execute(UUID cycleId) {
        log.info("Iniciando revisão das requisições para o ciclo com ID: {}", cycleId);

        List<WeeklyRequestDetailsResponse> requests = repository.findAllByCycleId(cycleId).stream()
                .map(request -> new WeeklyRequestDetailsResponse(
                        request.getId().value(),
                        request.getServiceCode(),
                        request.getCycleId(),
                        request.getObservation(),
                        request.getCreatedAt(),
                        request.getItems().stream()
                                .map(item -> new RequestItemDetailsResponse(item.productId(), item.quantity()))
                                .toList()
                ))
                .toList();

        log.info("Revisão de requisições do ciclo {} concluída. Total de requisições encontradas: {}", cycleId, requests.size());

        return requests;
    }
}
