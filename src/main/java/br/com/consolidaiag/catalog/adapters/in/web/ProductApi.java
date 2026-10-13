package br.com.consolidaiag.catalog.adapters.in.web;

import br.com.consolidaiag.catalog.application.dtos.request.EditProductDetailsPayload;
import br.com.consolidaiag.catalog.application.dtos.request.RegisterProductPayload;
import br.com.consolidaiag.catalog.application.dtos.response.CatalogProductResponse;
import br.com.consolidaiag.catalog.application.dtos.response.RegisterProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag(name = "Product Catalog", description = "Endpoints para o gerenciamento do catálogo institucional de produtos e insumos do IAG")
public interface ProductApi {

    @Operation(
            summary = "Cadastrar produto no catálogo",
            description = "Cadastra um novo produto ativo no catálogo institucional com nome único e unidade de medida predefinida (RN-02.1, RN-02.10)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Produto cadastrado com sucesso",
                    content = @Content(schema = @Schema(implementation = RegisterProductResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados do produto inválidos (falha de validação)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Já existe um produto com o nome informado no catálogo",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content
            )
    })
    ResponseEntity<RegisterProductResponse> registerProduct(@Valid @RequestBody RegisterProductPayload payload);

    @Operation(
            summary = "Listar produtos do catálogo",
            description = "Retorna todos os produtos registrados no catálogo institucional para composição das solicitações semanais."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de produtos retornada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content
            )
    })
    ResponseEntity<List<CatalogProductResponse>> listCatalogProducts();

    @Operation(
            summary = "Editar detalhes do produto",
            description = "Altera o nome e a unidade de medida padrão de um produto existente no catálogo."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Detalhes do produto alterados com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos para alteração",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado com o ID fornecido",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Já existe outro produto com o nome informado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content
            )
    })
    ResponseEntity<Void> editProductDetails(
            @Parameter(description = "Identificador único do produto", required = true) @PathVariable UUID id,
            @Valid @RequestBody EditProductDetailsPayload payload
    );

    @Operation(
            summary = "Ativar produto no catálogo",
            description = "Torna o produto ativo para seleção nas solicitações semanais de compras das cozinhas."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Produto ativado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Produto já se encontra ativo",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado com o ID fornecido",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content
            )
    })
    ResponseEntity<Void> activateProduct(
            @Parameter(description = "Identificador único do produto", required = true) @PathVariable UUID id
    );

    @Operation(
            summary = "Desativar produto no catálogo",
            description = "Desativa o produto para novas solicitações (RN-02.9). Não afeta solicitações de ciclos históricos fechados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Produto desativado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Produto já se encontra inativo",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado com o ID fornecido",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content
            )
    })
    ResponseEntity<Void> deactivateProduct(
            @Parameter(description = "Identificador único do produto", required = true) @PathVariable UUID id
    );
}
