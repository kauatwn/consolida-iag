package br.com.consolidaiag.catalog.application.ports.in;

import br.com.consolidaiag.catalog.application.dtos.request.DeactivateProductPayload;

public interface DeactivateProductUseCase {

    void execute(DeactivateProductPayload payload);
}
