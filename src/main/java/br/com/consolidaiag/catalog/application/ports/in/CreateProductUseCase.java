package br.com.consolidaiag.catalog.application.ports.in;

import br.com.consolidaiag.catalog.application.dtos.request.CreateProductPayload;
import br.com.consolidaiag.catalog.application.dtos.response.CreateProductResponse;

public interface CreateProductUseCase {
    CreateProductResponse execute(CreateProductPayload payload);
}