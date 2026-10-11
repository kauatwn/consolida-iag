package br.com.consolidaiag.catalog.application.ports.in;

import br.com.consolidaiag.catalog.application.dtos.request.EditProductDetailsPayLoad;

public interface EditProductDetailsUseCase {

    void execute(EditProductDetailsPayLoad payload);
}
