package br.com.consolidaiag.cycle.adapters.in.web;

import br.com.consolidaiag.cycle.application.dtos.request.OpenWeeklyCyclePayload;
import br.com.consolidaiag.cycle.application.dtos.response.WeeklyCycleResponse;
import br.com.consolidaiag.cycle.application.ports.in.CloseWeeklyCycleUseCase;
import br.com.consolidaiag.cycle.application.ports.in.OpenWeeklyCycleUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cycles")
public class WeeklyCycleController implements WeeklyCycleApi {

    private final OpenWeeklyCycleUseCase openWeeklyCycleUseCase;
    private final CloseWeeklyCycleUseCase closeWeeklyCycleUseCase;

    public WeeklyCycleController(OpenWeeklyCycleUseCase openWeeklyCycleUseCase, CloseWeeklyCycleUseCase closeWeeklyCycleUseCase) {
        this.openWeeklyCycleUseCase = openWeeklyCycleUseCase;
        this.closeWeeklyCycleUseCase = closeWeeklyCycleUseCase;
    }

    @Override
    @PostMapping
    public ResponseEntity<WeeklyCycleResponse> openCycle(@Valid @RequestBody OpenWeeklyCyclePayload payload) {
        WeeklyCycleResponse response = openWeeklyCycleUseCase.execute(payload);

        // Retorna 201 Created com o cabeçalho 'Location' apontando para o recurso criado
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

        // Retorna 200 OK
        return ResponseEntity.ok(response);
    }
}