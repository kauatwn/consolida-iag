package br.com.consolidaiag.consolidation.adapters.in.web;

import br.com.consolidaiag.consolidation.application.dtos.response.ConsolidatedReportResponse;
import br.com.consolidaiag.consolidation.application.ports.in.ConsolidateCycleRequestsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ConsolidationController implements ConsolidationApi {

    private final ConsolidateCycleRequestsUseCase consolidateCycleRequestsUseCase;

    public ConsolidationController(ConsolidateCycleRequestsUseCase consolidateCycleRequestsUseCase) {
        this.consolidateCycleRequestsUseCase = consolidateCycleRequestsUseCase;
    }

    @Override
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<ConsolidatedReportResponse> getConsolidatedReport(UUID cycleId, String search) {
        ConsolidatedReportResponse response = consolidateCycleRequestsUseCase.execute(cycleId, search);
        return ResponseEntity.ok(response);
    }
}
