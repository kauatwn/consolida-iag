package br.com.consolidaiag.consolidation.application.dtos.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ConsolidatedReportResponse(
        UUID cycleId,
        Instant generatedAt,
        int totalProductsCount,
        List<ConsolidatedItemResponse> items
) {
}