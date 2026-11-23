package br.com.consolidaiag.consolidation.application.service;

import br.com.consolidaiag.consolidation.application.dtos.response.ConsolidatedItemResponse;
import br.com.consolidaiag.consolidation.application.dtos.response.ConsolidatedReportResponse;
import br.com.consolidaiag.consolidation.application.ports.in.ConsolidateCycleRequestsUseCase;
import br.com.consolidaiag.consolidation.application.ports.out.ConsolidationQueryRepositoryPort;
import br.com.consolidaiag.weeklycycle.application.ports.out.WeeklyCycleRepositoryPort;
import br.com.consolidaiag.weeklycycle.domain.exceptions.WeeklyCycleNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ConsolidateCycleRequestsService implements ConsolidateCycleRequestsUseCase {

    private final ConsolidationQueryRepositoryPort repository;
    private final WeeklyCycleRepositoryPort cycleRepository;

    public ConsolidateCycleRequestsService(
            ConsolidationQueryRepositoryPort repository,
            WeeklyCycleRepositoryPort cycleRepository
    ) {
        this.repository = repository;
        this.cycleRepository = cycleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ConsolidatedReportResponse execute(UUID cycleId, String search) {
        cycleRepository.findById(cycleId)
                .orElseThrow(() -> new WeeklyCycleNotFoundException(cycleId));

        List<ConsolidatedItemResponse> items = repository.findConsolidatedItemsByCycleId(cycleId, search);

        return new ConsolidatedReportResponse(
                cycleId,
                Instant.now(),
                items.size(),
                items
        );
    }
}
