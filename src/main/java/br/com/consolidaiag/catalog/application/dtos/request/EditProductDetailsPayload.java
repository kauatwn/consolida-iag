package br.com.consolidaiag.catalog.application.dtos.request;

import br.com.consolidaiag.catalog.domain.UnitMeasure;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EditProductDetailsPayload(
        UUID productId,

        @NotBlank(message = "O novo nome do produto é obrigatório")
        String newName,

        @NotNull(message = "A nova unidade de medida é obrigatória")
        UnitMeasure newUnitMeasure
) {
    public EditProductDetailsPayload(UUID productId, EditProductDetailsPayload body) {
        this(productId, body.newName(), body.newUnitMeasure());
    }
}
