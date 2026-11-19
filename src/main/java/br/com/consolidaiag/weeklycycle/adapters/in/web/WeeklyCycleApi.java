package br.com.consolidaiag.weeklycycle.adapters.in.web;

import br.com.consolidaiag.weeklycycle.application.dtos.request.OpenWeeklyCyclePayload;
import br.com.consolidaiag.weeklycycle.application.dtos.response.WeeklyCycleResponse;
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

import java.util.UUID;

@Tag(name = "Weekly Cycles", description = "Endpoints para o motor de tempo (gerenciamento de janelas de compras)")
public interface WeeklyCycleApi {

    @Operation(
            summary = "Abrir um novo ciclo",
            description = "Abre um novo ciclo de compras (RN-01: Apenas um ciclo pode estar aberto por vez)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ciclo criado e aberto com sucesso", content = @Content(schema = @Schema(implementation = WeeklyCycleResponse.class))),
            @ApiResponse(responseCode = "400", description = "Falha de validação ou ciclo já existente (Regra de negócio violada)", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    ResponseEntity<WeeklyCycleResponse> openCycle(@Valid @RequestBody OpenWeeklyCyclePayload payload);

    @Operation(
            summary = "Fechar ciclo aberto",
            description = "Fecha definitivamente um ciclo de compras, tornando as solicitações nele contidas imutáveis."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ciclo fechado com sucesso", content = @Content(schema = @Schema(implementation = WeeklyCycleResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ciclo já se encontra fechado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Ciclo não encontrado pelo ID informado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    ResponseEntity<WeeklyCycleResponse> closeCycle(@PathVariable UUID id);

    @Operation(
            summary = "Consultar ciclo ativo",
            description = "Consulta os detalhes do ciclo de compras atualmente aberto. Utilizado pela interface das cozinhas e pela validação de pedidos para verificar a janela de compras liberada."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ciclo ativo retornado com sucesso",
                    content = @Content(schema = @Schema(implementation = WeeklyCycleResponse.class))
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Não há ciclo aberto no momento",
                    content = @Content
            )
    })
    ResponseEntity<WeeklyCycleResponse> consultActiveCycle();
}