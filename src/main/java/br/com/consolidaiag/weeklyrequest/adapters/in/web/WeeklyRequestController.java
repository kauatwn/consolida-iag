package br.com.consolidaiag.weeklyrequest.adapters.in.web;

import br.com.consolidaiag.shared.adapters.in.security.AuthenticatedUser;
import br.com.consolidaiag.weeklyrequest.application.dtos.request.EditWeeklyRequestPayload;
import br.com.consolidaiag.weeklyrequest.application.dtos.request.SubmitWeeklyRequestPayload;
import br.com.consolidaiag.weeklyrequest.application.dtos.response.SubmitWeeklyRequestResponse;
import br.com.consolidaiag.weeklyrequest.application.dtos.response.WeeklyRequestDetailsResponse;
import br.com.consolidaiag.weeklyrequest.application.ports.in.ConsultActiveRequestUseCase;
import br.com.consolidaiag.weeklyrequest.application.ports.in.EditWeeklyRequestUseCase;
import br.com.consolidaiag.weeklyrequest.application.ports.in.ReviewCycleRequestsUseCase;
import br.com.consolidaiag.weeklyrequest.application.ports.in.SubmitWeeklyRequestUseCase;
import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;
import br.com.consolidaiag.weeklyrequest.domain.exceptions.ServiceAccessDeniedException;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/requests")
public class WeeklyRequestController implements WeeklyRequestApi {

    private final SubmitWeeklyRequestUseCase submitWeeklyRequestUseCase;
    private final EditWeeklyRequestUseCase editWeeklyRequestUseCase;
    private final ConsultActiveRequestUseCase consultActiveRequestUseCase;
    private final ReviewCycleRequestsUseCase reviewCycleRequestsUseCase;

    public WeeklyRequestController(
            SubmitWeeklyRequestUseCase submitWeeklyRequestUseCase,
            EditWeeklyRequestUseCase editWeeklyRequestUseCase,
            ConsultActiveRequestUseCase consultActiveRequestUseCase,
            ReviewCycleRequestsUseCase reviewCycleRequestsUseCase
    ) {
        this.submitWeeklyRequestUseCase = submitWeeklyRequestUseCase;
        this.editWeeklyRequestUseCase = editWeeklyRequestUseCase;
        this.consultActiveRequestUseCase = consultActiveRequestUseCase;
        this.reviewCycleRequestsUseCase = reviewCycleRequestsUseCase;
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('KITCHEN_LEADER', 'BUYER')")
    public ResponseEntity<SubmitWeeklyRequestResponse> submit(
            @Valid @RequestBody SubmitWeeklyRequestPayload payload,
            @Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        validateServiceAccess(currentUser, payload.serviceCode());

        SubmitWeeklyRequestResponse response = submitWeeklyRequestUseCase.execute(payload);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('KITCHEN_LEADER', 'BUYER')")
    public ResponseEntity<SubmitWeeklyRequestResponse> edit(
            @PathVariable UUID id,
            @Valid @RequestBody EditWeeklyRequestPayload payload
    ) {
        SubmitWeeklyRequestResponse response = editWeeklyRequestUseCase.execute(id, payload);

        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/active/{serviceCode}")
    @PreAuthorize("hasAnyRole('KITCHEN_LEADER', 'BUYER')")
    public ResponseEntity<WeeklyRequestDetailsResponse> consultActiveRequest(
            @PathVariable ServiceCode serviceCode,
            @Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        validateServiceAccess(currentUser, serviceCode);

        return consultActiveRequestUseCase.execute(serviceCode)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @Override
    @GetMapping("/cycle/{cycleId}")
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<List<WeeklyRequestDetailsResponse>> reviewCycleRequests(@PathVariable UUID cycleId) {
        List<WeeklyRequestDetailsResponse> responses = reviewCycleRequestsUseCase.execute(cycleId);

        return ResponseEntity.ok(responses);
    }

    private void validateServiceAccess(AuthenticatedUser currentUser, ServiceCode targetService) {
        if (currentUser == null || !currentUser.canAccessService(targetService)) {
            throw new ServiceAccessDeniedException(targetService);
        }
    }
}
