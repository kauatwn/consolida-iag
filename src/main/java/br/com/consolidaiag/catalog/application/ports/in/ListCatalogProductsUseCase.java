package br.com.consolidaiag.catalog.application.ports.in;

import br.com.consolidaiag.catalog.application.dtos.response.CatalogProductResponse;

import java.util.List;

public interface ListCatalogProductsUseCase {

    List<CatalogProductResponse> execute();
}
