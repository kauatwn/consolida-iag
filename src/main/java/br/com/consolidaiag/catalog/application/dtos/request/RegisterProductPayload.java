package br.com.consolidaiag.catalog.application.dtos.request;

import br.com.consolidaiag.catalog.domain.UnitMeasure;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterProductPayload(
        @NotBlank(message = "O nome do produto é obrigatório")
        String name,

        @NotNull(message = "A unidade de medida é obrigatória")
        UnitMeasure unitMeasure
) {
}