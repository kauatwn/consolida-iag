package br.com.consolidaiag.catalog.application.ports.in;

import br.com.consolidaiag.catalog.application.dtos.request.ActivateProductPayload;

public interface ActivateProductUseCase {

    void execute(ActivateProductPayload payload);
}
