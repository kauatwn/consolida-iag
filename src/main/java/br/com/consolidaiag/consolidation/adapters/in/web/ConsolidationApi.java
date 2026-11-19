package br.com.consolidaiag.consolidation.adapters.in.web;

import br.com.consolidaiag.consolidation.application.dtos.response.ConsolidatedReportResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Consolidation", description = "Endpoints de projeção e relatórios consolidados de solicitações de compras")
@RequestMapping("/api/v1/consolidation")
public interface ConsolidationApi {

    @Operation(
            summary = "Obter relatório consolidado sintético do ciclo",
            description = "Retorna a soma matemática agregada de todos os insumos solicitados pelas cozinhas (CEI, CCA, CJ) para um determinado ciclo de compras."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relatório consolidado gerado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado (Token JWT ausente ou inválido)"),
            @ApiResponse(responseCode = "404", description = "Ciclo de compras não encontrado")
    })
    @GetMapping("/cycles/{cycleId}")
    ResponseEntity<ConsolidatedReportResponse> getConsolidatedReport(
            @Parameter(description = "ID do ciclo de compras", required = true)
            @PathVariable UUID cycleId,

            @Parameter(description = "Filtro opcional para busca por nome do produto")
            @RequestParam(required = false) String search
    );
}