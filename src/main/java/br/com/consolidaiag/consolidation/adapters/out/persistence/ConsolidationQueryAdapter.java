package br.com.consolidaiag.consolidation.adapters.out.persistence;

import br.com.consolidaiag.consolidation.application.dtos.response.ConsolidatedItemResponse;
import br.com.consolidaiag.consolidation.application.ports.out.ConsolidationQueryRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ConsolidationQueryAdapter implements ConsolidationQueryRepositoryPort {

    private final ConsolidationQueryJpaRepository queryRepository;

    public ConsolidationQueryAdapter(ConsolidationQueryJpaRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @Override
    public List<ConsolidatedItemResponse> findConsolidatedItemsByCycleId(UUID cycleId, String search) {
        return queryRepository.findConsolidatedItemsByCycleId(cycleId, search);
    }
}