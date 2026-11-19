package br.com.consolidaiag.consolidation.application.ports.in;

import br.com.consolidaiag.consolidation.application.dtos.response.ConsolidatedReportResponse;

import java.util.UUID;

public interface ConsolidateCycleRequestsUseCase {
    ConsolidatedReportResponse execute(UUID cycleId, String search);
}