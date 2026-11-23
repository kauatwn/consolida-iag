package br.com.consolidaiag.consolidation.adapters.out.persistence;

import br.com.consolidaiag.consolidation.application.dtos.response.ConsolidatedItemResponse;
import br.com.consolidaiag.consolidation.application.dtos.response.ServiceBreakdownItemResponse;
import br.com.consolidaiag.consolidation.application.ports.out.ConsolidationQueryRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ConsolidationQueryAdapter implements ConsolidationQueryRepositoryPort {

    private final ConsolidationQueryJpaRepository queryRepository;

    public ConsolidationQueryAdapter(ConsolidationQueryJpaRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @Override
    public List<ConsolidatedItemResponse> findConsolidatedItemsByCycleId(UUID cycleId, String search) {
        List<ConsolidatedItemResponse> aggregatedItems = queryRepository.findConsolidatedItemsByCycleId(cycleId, search);
        List<ItemBreakdownProjection> breakdowns = queryRepository.findBreakdownByCycleId(cycleId);

        Map<UUID, List<ServiceBreakdownItemResponse>> breakdownMap = breakdowns.stream()
                .collect(Collectors.groupingBy(
                        ItemBreakdownProjection::productId,
                        Collectors.mapping(
                                b -> new ServiceBreakdownItemResponse(b.serviceCode(), b.quantity()),
                                Collectors.toList()
                        )
                ));

        return aggregatedItems.stream()
                .map(item -> new ConsolidatedItemResponse(
                        item.productId(),
                        item.productName(),
                        item.unitMeasure(),
                        item.category(),
                        item.totalQuantity(),
                        breakdownMap.getOrDefault(item.productId(), List.of())
                ))
                .toList();
    }
}
