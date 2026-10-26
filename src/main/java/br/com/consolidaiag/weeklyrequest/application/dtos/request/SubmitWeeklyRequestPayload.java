package br.com.consolidaiag.weeklyrequest.application.dtos.request;

import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Payload utilizado para submeter uma nova requisição semanal vinculada ao ciclo ativo.
 */
@Schema(description = "Payload para submissão de uma nova requisição semanal")
public record SubmitWeeklyRequestPayload(

        @NotNull(message = "O serviço solicitante é obrigatório")
        @Schema(
                description = "Código do serviço ou setor solicitante",
                example = "RESTAURANT",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        ServiceCode serviceCode,

        @Schema(
                description = "Observações gerais ou instruções adicionais para a submissão",
                example = "Entrega prioritária para o turno da manhã.",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String observation,

        @NotEmpty(message = "A solicitação deve conter pelo menos um item")
        @Schema(
                description = "Lista de itens que compõem a requisição semanal",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        List<@Valid ItemPayload> items
) {
}