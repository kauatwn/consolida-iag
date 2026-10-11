package br.com.consolidaiag.catalog.application.dtos.response;

import java.util.UUID;

public record CatalogProductResponse(
        UUID id,
        String name,
        String unitMeasure,
        boolean active
) {
}