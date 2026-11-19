package br.com.consolidaiag.consolidation.application.ports.out;

import br.com.consolidaiag.consolidation.application.dtos.response.ConsolidatedItemResponse;
import br.com.consolidaiag.consolidation.application.dtos.response.DetailedConsolidatedItemResponse;

import java.util.List;
import java.util.UUID;

public interface ConsolidationQueryRepositoryPort {
    List<ConsolidatedItemResponse> findConsolidatedItemsByCycleId(UUID cycleId, String search);

//    List<DetailedConsolidatedItemResponse> findDetailedConsolidatedItems(UUID cycleId, String serviceCode, String search);
}