package br.com.consolidaiag.catalog.adapters.in.web;

import br.com.consolidaiag.catalog.application.dtos.request.CreateProductPayload;
import br.com.consolidaiag.catalog.application.dtos.response.CreateProductResponse;
import br.com.consolidaiag.catalog.application.ports.in.CreateProductUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;

    public ProductController(CreateProductUseCase createProductUseCase) {
        this.createProductUseCase = createProductUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateProductResponse> createProduct(@Valid @RequestBody CreateProductPayload payload) {

        CreateProductResponse response = createProductUseCase.execute(payload);

        // Segue a mesma boa prática REST que utilizou no subdomínio anterior
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }
}