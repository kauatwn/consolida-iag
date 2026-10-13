package br.com.consolidaiag.catalog.application.ports.in;

import br.com.consolidaiag.catalog.application.dtos.request.EditProductDetailsPayload;

public interface EditProductDetailsUseCase {

    void execute(EditProductDetailsPayload payload);
}
