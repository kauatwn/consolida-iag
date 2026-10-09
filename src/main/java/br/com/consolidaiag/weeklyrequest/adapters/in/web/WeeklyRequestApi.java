package br.com.consolidaiag.weeklyrequest.adapters.in.web;

import br.com.consolidaiag.weeklyrequest.application.dtos.request.SubmitWeeklyRequestPayload;
import br.com.consolidaiag.weeklyrequest.application.dtos.response.SubmitWeeklyRequestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Weekly Requests", description = "Endpoints para gerenciamento de solicitações semanais")
public interface WeeklyRequestApi {

    @Operation(
            summary = "Submeter solicitação semanal",
            description = "Processa e envia uma nova solicitação semanal com os dados fornecidos."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Solicitação criada com sucesso",
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
}
