package br.com.consolidaiag.weeklyrequest.application.dtos.response;

import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO de resposta contendo os detalhes completos de uma requisição semanal.
 */
@Schema(description = "Detalhes completos de uma requisição semanal, incluindo seus itens e observações")
public record WeeklyRequestDetailsResponse(

        @Schema(
                description = "Identificador único (UUID) da requisição",
                example = "c56a4180-65aa-42ec-a945-5fd21dec0538"
        )
        UUID id,

        @Schema(
                description = "Código do serviço ou setor responsável pela solicitação",
                example = "CEI, CCA, CJ"
        )
        ServiceCode serviceCode,

        @Schema(
                description = "Identificador único (UUID) do ciclo semanal associado",
                example = "123e4567-e89b-12d3-a456-426614174000"
        )
        UUID cycleId,

        @Schema(
                description = "Observações gerais ou instruções adicionais informadas na requisição",
                example = "Favor entregar no período da manhã."
        )
        String observation,

        @Schema(
                description = "Data e horário em que a requisição foi registrada",
                example = "2026-10-26T14:30:00"
        )
        LocalDateTime createdAt,

        @Schema(
                description = "Lista detalhada de itens que compõem esta requisição semanal"
        )
        List<RequestItemDetailsResponse> items
) {
}