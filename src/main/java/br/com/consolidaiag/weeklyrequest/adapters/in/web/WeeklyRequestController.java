package br.com.consolidaiag.weeklyrequest.adapters.in.web;

import br.com.consolidaiag.weeklyrequest.application.dtos.request.SubmitWeeklyRequestPayload;
import br.com.consolidaiag.weeklyrequest.application.dtos.response.SubmitWeeklyRequestResponse;
import br.com.consolidaiag.weeklyrequest.application.ports.in.SubmitWeeklyRequestUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/requests")
public class WeeklyRequestController {

    private final SubmitWeeklyRequestUseCase submitWeeklyRequestUseCase;

    public WeeklyRequestController(SubmitWeeklyRequestUseCase submitWeeklyRequestUseCase) {
        this.submitWeeklyRequestUseCase = submitWeeklyRequestUseCase;
    }

    @PostMapping
    public ResponseEntity<SubmitWeeklyRequestResponse> submit(@Valid @RequestBody SubmitWeeklyRequestPayload payload) {
        SubmitWeeklyRequestResponse response = submitWeeklyRequestUseCase.execute(payload);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
                
        return ResponseEntity.created(location).body(response);
    }
}