package br.com.consolidaiag.catalog.application.dtos.request;

import br.com.consolidaiag.catalog.domain.ProductCategory;
import br.com.consolidaiag.catalog.domain.UnitMeasure;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Payload utilizado para solicitar a edição dos detalhes de um produto existente no catálogo.
 */
@Schema(description = "Payload para edição de nome e unidade de medida de um produto no catálogo")
public record EditProductDetailsPayload(

        @Schema(
                description = "Identificador único (UUID) do produto cujos detalhes serão editados",
                example = "123e4567-e89b-12d3-a456-426614174000",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        UUID productId,

        @NotBlank(message = "O novo nome do produto é obrigatório")
        @Schema(
                description = "Novo nome que será atribuído ao produto",
                example = "Arroz Integral Premium 5kg",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String newName,

        @NotNull(message = "A nova unidade de medida é obrigatória")
        @Schema(
                description = "Nova unidade de medida do produto (ex: KG, UN, LITRO)",
                example = "KG",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        UnitMeasure newUnitMeasure,
        @NotBlank(message = "A nova categoria do produto é obrigatória")
        @Schema(
                description = "Nova categoria do produto (ex: ALIMENTOS, BEBIDAS, HIGIENE)",
                example = "ALIMENTOS",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        ProductCategory newCategory
) {
    public EditProductDetailsPayload(UUID productId, EditProductDetailsPayload body) {
        this(productId, body.newName(), body.newUnitMeasure(), body.newCategory());
    }
}