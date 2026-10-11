package br.com.consolidaiag.catalog.adapters.in.web;

import br.com.consolidaiag.catalog.application.dtos.request.RegisterProductPayload;
import br.com.consolidaiag.catalog.application.dtos.response.RegisterProductResponse;
import br.com.consolidaiag.catalog.application.ports.in.RegisterProductUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final RegisterProductUseCase registerProductUseCase;

    public ProductController(RegisterProductUseCase registerProductUseCase) {
        this.registerProductUseCase = registerProductUseCase;
    }

    @PostMapping
    public ResponseEntity<RegisterProductResponse> createProduct(@Valid @RequestBody RegisterProductPayload payload) {

        RegisterProductResponse response = registerProductUseCase.execute(payload);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }
}