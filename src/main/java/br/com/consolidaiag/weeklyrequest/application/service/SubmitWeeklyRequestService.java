package br.com.consolidaiag.weeklyrequest.application.service;

import br.com.consolidaiag.weeklyrequest.application.dtos.request.SubmitWeeklyRequestPayload;
import br.com.consolidaiag.weeklyrequest.application.dtos.response.SubmitWeeklyRequestResponse;
import br.com.consolidaiag.weeklyrequest.application.policies.WeeklyRequestPolicy;
import br.com.consolidaiag.weeklyrequest.application.ports.in.SubmitWeeklyRequestUseCase;
import br.com.consolidaiag.weeklyrequest.application.ports.out.ActiveCyclePort;
import br.com.consolidaiag.weeklyrequest.application.ports.out.WeeklyRequestRepositoryPort;
import br.com.consolidaiag.weeklyrequest.domain.RequestItem;
import br.com.consolidaiag.weeklyrequest.domain.WeeklyRequest;
import br.com.consolidaiag.weeklyrequest.domain.exceptions.ActiveCycleNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SubmitWeeklyRequestService implements SubmitWeeklyRequestUseCase {

    private final ActiveCyclePort cyclePort;
    private final WeeklyRequestRepositoryPort repository;
    private final List<WeeklyRequestPolicy> policies;

    public SubmitWeeklyRequestService(
            ActiveCyclePort cyclePort,
            WeeklyRequestRepositoryPort repository,
            List<WeeklyRequestPolicy> policies
    ) {
        this.cyclePort = cyclePort;
        this.repository = repository;
        this.policies = policies != null ? List.copyOf(policies) : List.of();
    }

    @Override
    @Transactional
    public SubmitWeeklyRequestResponse execute(SubmitWeeklyRequestPayload payload) {
        if (payload == null) {
            throw new IllegalArgumentException("Payload cannot be null.");
        }

        // RN-02.5: Resolução do ciclo semanal ativo
        UUID activeCycleId = cyclePort.getActiveCycleId()
                .orElseThrow(ActiveCycleNotFoundException::new);

        // Mapeia os itens do payload para a entidade de domínio
        List<RequestItem> items = payload.items().stream()
                .map(item -> new RequestItem(item.productId(), item.quantity()))
                .toList();

        // Criação da entidade de domínio
        WeeklyRequest request = WeeklyRequest.create(
                payload.serviceCode(),
                activeCycleId,
                payload.observation(),
                items
        );

        // Executa todas as políticas de validação registradas (Validation Policies)
        policies.forEach(policy -> policy.validate(request));

        WeeklyRequest saved = repository.save(request);

        return new SubmitWeeklyRequestResponse(
                saved.getId().value(),
                saved.getServiceCode(),
                saved.getCycleId(),
                saved.getCreatedAt()
        );
    }
}