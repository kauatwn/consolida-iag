package br.com.consolidaiag.weeklyrequest.adapters.in.web;

import br.com.consolidaiag.weeklyrequest.application.dtos.request.EditWeeklyRequestPayload;
import br.com.consolidaiag.weeklyrequest.application.dtos.request.SubmitWeeklyRequestPayload;
import br.com.consolidaiag.weeklyrequest.application.dtos.response.SubmitWeeklyRequestResponse;
import br.com.consolidaiag.weeklyrequest.application.dtos.response.WeeklyRequestDetailsResponse;
import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Tag(name = "Weekly Requests", description = "Endpoints para o gerenciamento de solicitações semanais de insumos")
public interface WeeklyRequestApi {

    @Operation(
            summary = "Submeter solicitação semanal",
            description = "Processa e envia uma nova solicitação semanal com os dados fornecidos pela Líder de Cozinha."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Solicitação submetida com sucesso",
                    content = @Content(schema = @Schema(implementation = SubmitWeeklyRequestResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de requisição inválidos (falha de validação)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no processamento da solicitação",
                    content = @Content
            )
    })
    ResponseEntity<SubmitWeeklyRequestResponse> submit(@Valid @RequestBody SubmitWeeklyRequestPayload payload);

    @Operation(
            summary = "Editar solicitação semanal",
            description = "Atualiza os itens e observações de uma solicitação existente. Apenas permitido caso o ciclo correspondente ainda esteja aberto."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Solicitação atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = SubmitWeeklyRequestResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou ciclo de compras já encontra-se fechado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Solicitação não encontrada",
                    content = @Content
            )
    })
    ResponseEntity<SubmitWeeklyRequestResponse> edit(
            @PathVariable UUID id,
            @Valid @RequestBody EditWeeklyRequestPayload payload
    );

    @Operation(
            summary = "Consultar pedido atual do serviço",
            description = "Consulta a solicitação feita pela Cozinha (CEI, CCA, CJ) no ciclo de compras que está atualmente aberto."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Solicitação encontrada e retornada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Nenhuma solicitação submetida pelo serviço no ciclo atual até o momento"
            )
    })
    ResponseEntity<WeeklyRequestDetailsResponse> consultActiveRequest(@PathVariable ServiceCode serviceCode);

    @Operation(
            summary = "Revisar pedidos do ciclo",
            description = "Retorna todas as solicitações consolidadas feitas por todas as cozinhas em um ciclo específico. Utilizado pela Gestora para auditoria e compras."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de solicitações retornada com sucesso"
    )
    ResponseEntity<List<WeeklyRequestDetailsResponse>> reviewCycleRequests(@PathVariable UUID cycleId);
}