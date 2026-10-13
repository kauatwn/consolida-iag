package br.com.consolidaiag.weeklyrequest.application.service;

import br.com.consolidaiag.weeklyrequest.application.dtos.response.RequestItemDetailsResponse;
import br.com.consolidaiag.weeklyrequest.application.dtos.response.WeeklyRequestDetailsResponse;
import br.com.consolidaiag.weeklyrequest.application.ports.in.ReviewCycleRequestsUseCase;
import br.com.consolidaiag.weeklyrequest.application.ports.out.WeeklyRequestRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewCycleRequestsService implements ReviewCycleRequestsUseCase {

    private final WeeklyRequestRepositoryPort repository;

    public ReviewCycleRequestsService(WeeklyRequestRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WeeklyRequestDetailsResponse> execute(UUID cycleId) {
        return repository.findAllByCycleId(cycleId).stream()
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
    }
}