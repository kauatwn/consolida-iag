package br.com.consolidaiag.weeklyrequest.application.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * Payload utilizado para solicitar a edição de uma requisição semanal existente.
 */
@Schema(description = "Payload para edição de uma requisição semanal")
public record EditWeeklyRequestPayload(

        @Schema(
                description = "Observações gerais ou instruções adicionais para a requisição",
                example = "Favor entregar no período da manhã.",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String observation,

        @NotEmpty(message = "A solicitação deve conter pelo menos um item")
        @Schema(
                description = "Lista de itens que compõem a requisição atualizada",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        List<@Valid ItemPayload> items
) {
}