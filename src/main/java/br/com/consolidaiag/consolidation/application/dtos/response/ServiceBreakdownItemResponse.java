package br.com.consolidaiag.consolidation.application.dtos.response;

import java.math.BigDecimal;

public record ServiceBreakdownItemResponse(
        String serviceCode,
        BigDecimal quantity
) {
}