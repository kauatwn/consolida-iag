package br.com.consolidaiag.consolidation.application.dtos.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ConsolidatedItemResponse(
        UUID productId,
        String productName,
        String unitMeasure,
        String category,
        BigDecimal totalQuantity,
        List<ServiceBreakdownItemResponse> breakdown
) {
    public ConsolidatedItemResponse(
            UUID productId,
            String productName,
            String unitMeasure,
            String category,
            BigDecimal totalQuantity
    ) {
        this(productId, productName, unitMeasure, category, totalQuantity, List.of());
    }

    public ConsolidatedItemResponse(
            UUID productId,
            String productName,
            String unitMeasure,
            BigDecimal totalQuantity
    ) {
        this(productId, productName, unitMeasure, null, totalQuantity, List.of());
    }
}
