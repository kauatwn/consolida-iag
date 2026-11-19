package br.com.consolidaiag.consolidation.application.dtos.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record DetailedConsolidatedReportResponse(
        UUID cycleId,
        Instant generatedAt,
        int totalProductsCount,
        List<DetailedConsolidatedItemResponse> items
) {
}