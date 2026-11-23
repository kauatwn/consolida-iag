package br.com.consolidaiag.consolidation.adapters.out.persistence;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemBreakdownProjection(
        UUID productId,
        String serviceCode,
        BigDecimal quantity
) {
}
