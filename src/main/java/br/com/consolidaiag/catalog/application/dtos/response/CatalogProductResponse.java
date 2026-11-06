package br.com.consolidaiag.catalog.application.dtos.response;

import br.com.consolidaiag.catalog.domain.ProductStatus;

import java.util.UUID;

public record CatalogProductResponse(
        UUID id,
        String name,
        String unitMeasure,
        ProductStatus productStatus
) {
}