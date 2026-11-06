package br.com.consolidaiag.catalog.application.dtos.response;

import br.com.consolidaiag.catalog.domain.ProductStatus;
import br.com.consolidaiag.catalog.domain.UnitMeasure;

import java.util.UUID;

public record RegisterProductResponse(
        UUID id,
        String name,
        UnitMeasure unitMeasure,
        ProductStatus productStatus
) {
}