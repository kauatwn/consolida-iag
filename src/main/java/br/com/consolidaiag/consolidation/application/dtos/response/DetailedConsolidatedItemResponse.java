package br.com.consolidaiag.consolidation.application.dtos.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record DetailedConsolidatedItemResponse(
        UUID productId,
        String productName,
        String unitMeasure,
        String category,
        BigDecimal totalQuantity,
        List<ServiceBreakdownItemResponse> breakdown
) {
}