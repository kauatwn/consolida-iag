package br.com.consolidaiag.weeklyrequest.application.service;

import br.com.consolidaiag.weeklyrequest.application.ports.in.SubmitWeeklyRequestUseCase;
import br.com.consolidaiag.weeklyrequest.application.ports.out.ActiveCyclePort;
import br.com.consolidaiag.weeklyrequest.application.ports.out.ProductCatalogPort;
import br.com.consolidaiag.weeklyrequest.application.ports.out.WeeklyRequestRepositoryPort;
import br.com.consolidaiag.weeklyrequest.application.dtos.request.ItemPayload;
import br.com.consolidaiag.weeklyrequest.application.dtos.request.SubmitWeeklyRequestPayload;
import br.com.consolidaiag.weeklyrequest.application.dtos.response.SubmitWeeklyRequestResponse;
import br.com.consolidaiag.weeklyrequest.domain.WeeklyRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubmitWeeklyRequestService implements SubmitWeeklyRequestUseCase {

    private final ActiveCyclePort cyclePort;
    private final ProductCatalogPort catalogPort;
    private final WeeklyRequestRepositoryPort repository;

    public SubmitWeeklyRequestService(
        ActiveCyclePort cyclePort, 
        ProductCatalogPort catalogPort,
        WeeklyRequestRepositoryPort repository
    ) {
        this.cyclePort = cyclePort;
        this.catalogPort = catalogPort;
        this.repository = repository;
    }

    @Override
    @Transactional
    public SubmitWeeklyRequestResponse execute(SubmitWeeklyRequestPayload payload) {
        if (payload == null) {
            throw new IllegalArgumentException("Payload cannot be null.");
        }

        // RN-02.5: Is there an open cycle?
        Long activeCycleId = cyclePort.getActiveCycleId()
            .orElseThrow(() -> new IllegalStateException("No open cycle available."));

        // RN-02.4: Did this service already submit a request?
        if (repository.existsByServiceCodeAndCycleId(payload.serviceCode(), activeCycleId)) {
            throw new IllegalStateException("Service already submitted a request for this cycle.");
        }

        // RN-02.1: Are all requested products active in the catalog?
        List<Long> requestedProductIds = payload.items().stream()
            .map(ItemPayload::productId)
            .toList();
            
        if (!catalogPort.areAllProductsActive(requestedProductIds)) {
            throw new IllegalArgumentException("One or more products are inactive or invalid.");
        }

        // Cria a entidade
        WeeklyRequest request = WeeklyRequest.create(
            payload.serviceCode(),
            activeCycleId,
            payload.observation()
        );

        // Adiciona os itens (As validações RN-02.2 e de duplicidade ocorrem aqui dentro do Domínio!)
        for (ItemPayload item : payload.items()) {
            request.addItem(item.productId(), item.quantity());
        }

        WeeklyRequest saved = repository.save(request);

        return new SubmitWeeklyRequestResponse(
            saved.getId(),
            saved.getServiceCode(),
            saved.getCycleId(),
            saved.getCreatedAt()
        );
    }
}