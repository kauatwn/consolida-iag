package br.com.consolidaiag.catalog.application.dtos.response;

import br.com.consolidaiag.catalog.domain.UnitMeasure;

public record CreateProductResponse(
    Long id, 
    String name, 
    UnitMeasure unitMeasure, 
    boolean active
) {}