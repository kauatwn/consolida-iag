package br.com.consolidaiag.catalog.application.ports.in;

import br.com.consolidaiag.catalog.application.dtos.request.RegisterProductPayload;
import br.com.consolidaiag.catalog.application.dtos.response.RegisterProductResponse;

public interface RegisterProductUseCase {
    RegisterProductResponse execute(RegisterProductPayload payload);
}