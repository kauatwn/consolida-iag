package br.com.consolidaiag.consolidation.adapters.in.web;

import br.com.consolidaiag.consolidation.application.dtos.response.ConsolidatedReportResponse;
import br.com.consolidaiag.consolidation.application.ports.in.ConsolidateCycleRequestsUseCase;
import br.com.consolidaiag.weeklycycle.application.ports.out.WeeklyCycleRepositoryPort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ConsolidationController implements ConsolidationApi {

    private final ConsolidateCycleRequestsUseCase consolidateCycleRequestsUseCase;
    private final WeeklyCycleRepositoryPort cycleRepository;

    public ConsolidationController(
            ConsolidateCycleRequestsUseCase consolidateCycleRequestsUseCase,
            WeeklyCycleRepositoryPort cycleRepository
    ) {
        this.consolidateCycleRequestsUseCase = consolidateCycleRequestsUseCase;
        this.cycleRepository = cycleRepository;
    }

    @Override
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<ConsolidatedReportResponse> getConsolidatedReport(UUID cycleId, String search) {
        ConsolidatedReportResponse response = consolidateCycleRequestsUseCase.execute(cycleId, search);
        return ResponseEntity.ok(response);
    }

    @Override
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<ConsolidatedReportResponse> getActiveConsolidatedReport(String search) {
        return cycleRepository.findActiveCycle()
                .map(activeCycle -> {
                    ConsolidatedReportResponse response = consolidateCycleRequestsUseCase.execute(activeCycle.getId().value(), search);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
