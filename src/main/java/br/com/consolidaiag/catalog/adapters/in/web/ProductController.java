package br.com.consolidaiag.catalog.adapters.in.web;

import br.com.consolidaiag.catalog.application.dtos.request.ActivateProductPayload;
import br.com.consolidaiag.catalog.application.dtos.request.DeactivateProductPayload;
import br.com.consolidaiag.catalog.application.dtos.request.EditProductDetailsPayload;
import br.com.consolidaiag.catalog.application.dtos.request.RegisterProductPayload;
import br.com.consolidaiag.catalog.application.dtos.response.CatalogProductResponse;
import br.com.consolidaiag.catalog.application.dtos.response.RegisterProductResponse;
import br.com.consolidaiag.catalog.application.ports.in.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController implements ProductApi {

    private final RegisterProductUseCase registerProductUseCase;
    private final ListCatalogProductsUseCase listCatalogProductsUseCase;
    private final EditProductDetailsUseCase editProductDetailsUseCase;
    private final ActivateProductUseCase activateProductUseCase;
    private final DeactivateProductUseCase deactivateProductUseCase;

    public ProductController(
            RegisterProductUseCase registerProductUseCase,
            ListCatalogProductsUseCase listCatalogProductsUseCase,
            EditProductDetailsUseCase editProductDetailsUseCase,
            ActivateProductUseCase activateProductUseCase,
            DeactivateProductUseCase deactivateProductUseCase
    ) {
        this.registerProductUseCase = registerProductUseCase;
        this.listCatalogProductsUseCase = listCatalogProductsUseCase;
        this.editProductDetailsUseCase = editProductDetailsUseCase;
        this.activateProductUseCase = activateProductUseCase;
        this.deactivateProductUseCase = deactivateProductUseCase;
    }

    @Override
    @PostMapping
    public ResponseEntity<RegisterProductResponse> registerProduct(@Valid @RequestBody RegisterProductPayload payload) {
        RegisterProductResponse response = registerProductUseCase.execute(payload);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CatalogProductResponse>> listCatalogProducts() {
        List<CatalogProductResponse> response = listCatalogProductsUseCase.execute();
        return ResponseEntity.ok(response);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Void> editProductDetails(
            @PathVariable UUID id,
            @Valid @RequestBody EditProductDetailsPayload payload
    ) {
        EditProductDetailsPayload command = new EditProductDetailsPayload(id, payload);
        editProductDetailsUseCase.execute(command);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateProduct(@PathVariable UUID id) {
        ActivateProductPayload payload = new ActivateProductPayload(id);
        activateProductUseCase.execute(payload);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateProduct(@PathVariable UUID id) {
        DeactivateProductPayload payload = new DeactivateProductPayload(id);
        deactivateProductUseCase.execute(payload);

        return ResponseEntity.noContent().build();
    }
}
