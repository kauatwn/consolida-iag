package br.com.consolidaiag.consolidation.application.dtos.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ConsolidatedItemResponse(
        UUID productId,
        String productName,
        String unitMeasure,
        BigDecimal totalQuantity
) {
}