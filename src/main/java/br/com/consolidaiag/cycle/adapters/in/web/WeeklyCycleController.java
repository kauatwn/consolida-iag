package br.com.consolidaiag.cycle.adapters.in.web;

import br.com.consolidaiag.cycle.application.dtos.request.OpenWeeklyCyclePayload;
import br.com.consolidaiag.cycle.application.dtos.response.WeeklyCycleResponse;
import br.com.consolidaiag.cycle.application.ports.in.CloseWeeklyCycleUseCase;
import br.com.consolidaiag.cycle.application.ports.in.ConsultActiveCycleUseCase;
import br.com.consolidaiag.cycle.application.ports.in.OpenWeeklyCycleUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cycles")
public class WeeklyCycleController implements WeeklyCycleApi {

    private final OpenWeeklyCycleUseCase openWeeklyCycleUseCase;
    private final CloseWeeklyCycleUseCase closeWeeklyCycleUseCase;
    private final ConsultActiveCycleUseCase consultActiveCycleUseCase;

    public WeeklyCycleController(OpenWeeklyCycleUseCase openWeeklyCycleUseCase, CloseWeeklyCycleUseCase closeWeeklyCycleUseCase, ConsultActiveCycleUseCase consultActiveCycleUseCase) {
        this.openWeeklyCycleUseCase = openWeeklyCycleUseCase;
        this.closeWeeklyCycleUseCase = closeWeeklyCycleUseCase;
        this.consultActiveCycleUseCase = consultActiveCycleUseCase;
    }

    @Override
    @PostMapping
    public ResponseEntity<WeeklyCycleResponse> openCycle(@Valid @RequestBody OpenWeeklyCyclePayload payload) {
        WeeklyCycleResponse response = openWeeklyCycleUseCase.execute(payload);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Override
    @PutMapping("/{id}/close")
    public ResponseEntity<WeeklyCycleResponse> closeCycle(@PathVariable UUID id) {
        WeeklyCycleResponse response = closeWeeklyCycleUseCase.execute(id);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<WeeklyCycleResponse> consultActiveCycle() {
        Optional<WeeklyCycleResponse> response = consultActiveCycleUseCase.execute();

        return response.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}