package br.com.consolidaiag.consolidation.application.ports.in;

import br.com.consolidaiag.consolidation.application.dtos.response.DetailedConsolidatedReportResponse;

import java.util.UUID;

public interface ConsultServiceBreakdownUseCase {
    DetailedConsolidatedReportResponse execute(UUID cycleId, String serviceCode, String category, String search);
}