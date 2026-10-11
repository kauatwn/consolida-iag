package br.com.consolidaiag.catalog.application.dtos.request;

import br.com.consolidaiag.catalog.domain.UnitMeasure;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EditProductDetailsPayLoad(
        @NotNull(message = "O ID do produto é obrigatório.")
        UUID productId,

        @NotNull(message = "O novo nome do produto é obrigatório.")
        @NotBlank(message = "O novo nome do produto não pode estar em branco.")
        String newName,

        @NotNull(message = "A nova unidade de medida é obrigatória.")
        UnitMeasure newUnitMeasure
) {
}
