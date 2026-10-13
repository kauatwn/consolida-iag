package br.com.consolidaiag.weeklyrequest.application.service;

import br.com.consolidaiag.weeklyrequest.application.dtos.request.EditWeeklyRequestPayload;
import br.com.consolidaiag.weeklyrequest.application.dtos.response.SubmitWeeklyRequestResponse;
import br.com.consolidaiag.weeklyrequest.application.policies.ActiveProductsOnlyPolicy;
import br.com.consolidaiag.weeklyrequest.application.policies.EditWeeklyRequestPolicy;
import br.com.consolidaiag.weeklyrequest.application.policies.WeeklyRequestPolicy;
import br.com.consolidaiag.weeklyrequest.application.ports.in.EditWeeklyRequestUseCase;
import br.com.consolidaiag.weeklyrequest.application.ports.out.FetchActiveCyclePort;
import br.com.consolidaiag.weeklyrequest.application.ports.out.WeeklyRequestRepositoryPort;
import br.com.consolidaiag.weeklyrequest.domain.RequestItem;
import br.com.consolidaiag.weeklyrequest.domain.WeeklyRequest;
import br.com.consolidaiag.weeklyrequest.domain.exceptions.ClosedCycleImmutableException;
import br.com.consolidaiag.weeklyrequest.domain.exceptions.WeeklyRequestNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class EditWeeklyRequestService implements EditWeeklyRequestUseCase {

    private final WeeklyRequestRepositoryPort repository;
    private final FetchActiveCyclePort fetchActiveCyclePort;
    private final List<WeeklyRequestPolicy> policies;

    public EditWeeklyRequestService(
            WeeklyRequestRepositoryPort repository,
            FetchActiveCyclePort fetchActiveCyclePort,
            List<EditWeeklyRequestPolicy> policies
    ) {
        this.repository = repository;
        this.fetchActiveCyclePort = fetchActiveCyclePort;
        this.policies = policies != null ? List.copyOf(policies) : List.of();
    }

    @Override
    @Transactional
    public SubmitWeeklyRequestResponse execute(UUID requestId, EditWeeklyRequestPayload payload) {
        // 1. Busca a solicitação existente
        WeeklyRequest request = repository.findById(requestId)
                .orElseThrow(() -> new WeeklyRequestNotFoundException(requestId));

        // 2. Busca o ID do ciclo que está ABERTO agora
        UUID currentActiveCycleId = fetchActiveCyclePort.getActiveCycleId().orElse(null);

        // 3. Validação de Imutabilidade (RN-02.5) usando a sua exceção
        if (!request.getCycleId().equals(currentActiveCycleId)) {
            throw new ClosedCycleImmutableException(request.getCycleId());
        }

        // 4. Mapeia e atualiza o domínio
        List<RequestItem> newItems = payload.items().stream()
                .map(item -> new RequestItem(item.productId(), item.quantity()))
                .toList();

        request.update(payload.observation(), newItems);

        // 5. Re-executa as políticas (ex: verificar se os novos produtos estão ativos no catálogo)
        policies.forEach(policy -> policy.validate(request));

        // 6. Salva e retorna
        WeeklyRequest saved = repository.save(request);

        return new SubmitWeeklyRequestResponse(
                saved.getId().value(),
                saved.getServiceCode(),
                saved.getCycleId(),
                saved.getCreatedAt()
        );
    }
}