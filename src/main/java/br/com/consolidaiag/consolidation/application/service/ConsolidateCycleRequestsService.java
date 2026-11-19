package br.com.consolidaiag.consolidation.application.service;

import br.com.consolidaiag.consolidation.application.dtos.response.ConsolidatedItemResponse;
import br.com.consolidaiag.consolidation.application.dtos.response.ConsolidatedReportResponse;
import br.com.consolidaiag.consolidation.application.ports.in.ConsolidateCycleRequestsUseCase;
import br.com.consolidaiag.consolidation.application.ports.out.ConsolidationQueryRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ConsolidateCycleRequestsService implements ConsolidateCycleRequestsUseCase {

    private final ConsolidationQueryRepositoryPort repository;

    public ConsolidateCycleRequestsService(ConsolidationQueryRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public ConsolidatedReportResponse execute(UUID cycleId, String search) {
        List<ConsolidatedItemResponse> items = repository.findConsolidatedItemsByCycleId(cycleId, search);

        return new ConsolidatedReportResponse(
                cycleId,
                Instant.now(),
                items.size(),
                items
        );
    }
}