package br.com.consolidaiag.catalog.application.dtos.request;

import br.com.consolidaiag.catalog.domain.UnitMeasure;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Payload utilizado para solicitar o cadastro de um novo produto no catálogo.
 */
@Schema(description = "Payload para o cadastro de um novo produto no catálogo")
public record RegisterProductPayload(

        @NotBlank(message = "O nome do produto é obrigatório")
        @Schema(
                description = "Nome descritivo do produto a ser cadastrado",
                example = "Feijão Preto Tipo 1 1kg",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String name,

        @NotNull(message = "A unidade de medida é obrigatória")
        @Schema(
                description = "Unidade de medida comercializada do produto (ex: KG, UN, LITRO)",
                example = "KG",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        UnitMeasure unitMeasure
) {
}