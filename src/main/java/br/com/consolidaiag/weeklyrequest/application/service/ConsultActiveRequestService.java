package br.com.consolidaiag.weeklyrequest.application.service;

import br.com.consolidaiag.weeklyrequest.application.dtos.response.RequestItemDetailsResponse;
import br.com.consolidaiag.weeklyrequest.application.dtos.response.WeeklyRequestDetailsResponse;
import br.com.consolidaiag.weeklyrequest.application.ports.in.ConsultActiveRequestUseCase;
import br.com.consolidaiag.weeklyrequest.application.ports.out.FetchActiveCyclePort;
import br.com.consolidaiag.weeklyrequest.application.ports.out.WeeklyRequestRepositoryPort;
import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ConsultActiveRequestService implements ConsultActiveRequestUseCase {

    private final WeeklyRequestRepositoryPort repository;
    private final FetchActiveCyclePort cyclePort;

    public ConsultActiveRequestService(WeeklyRequestRepositoryPort repository, FetchActiveCyclePort cyclePort) {
        this.repository = repository;
        this.cyclePort = cyclePort;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WeeklyRequestDetailsResponse> execute(ServiceCode serviceCode) {
        return cyclePort.getActiveCycleId()
                .flatMap(cycleId -> repository.findByServiceCodeAndCycleId(serviceCode, cycleId))
                .map(request -> new WeeklyRequestDetailsResponse(
                        request.getId().value(),
                        request.getServiceCode(),
                        request.getCycleId(),
                        request.getObservation(),
                        request.getCreatedAt(),
                        request.getItems().stream()
                                .map(item -> new RequestItemDetailsResponse(item.productId(), item.quantity()))
                                .toList()
                ));
    }
}