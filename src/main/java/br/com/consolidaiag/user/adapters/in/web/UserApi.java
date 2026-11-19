package br.com.consolidaiag.user.adapters.in.web;

import br.com.consolidaiag.shared.adapters.in.security.AuthenticatedUser;
import br.com.consolidaiag.user.application.dtos.request.RegisterUserPayload;
import br.com.consolidaiag.user.application.dtos.response.UserDetailsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Users & Authentication", description = "Endpoints para gerenciamento de usuários (Líderes de Cozinha e Gestoras) integrados ao Firebase Auth")
public interface UserApi {

    @Operation(summary = "Cadastrar Líder de Cozinha ou Gestora", description = "Registra um novo perfil de usuário vinculando-o ao seu respectivo serviço (CEI, CCA, CJ) ou perfil de Gestora.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserDetailsResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados cadastrais inválidos", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso restrito a Gestoras", content = @Content),
            @ApiResponse(responseCode = "409", description = "E-mail já cadastrado no sistema", content = @Content)
    })
    ResponseEntity<UserDetailsResponse> register(@Valid @RequestBody RegisterUserPayload payload);

    @Operation(summary = "Consultar usuário logado", description = "Retorna os dados, perfil e serviço do usuário atualmente autenticado via Firebase Bearer Token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do usuário autenticado",
                    content = @Content(schema = @Schema(implementation = UserDetailsResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário autenticado no Firebase, mas ainda não cadastrado no sistema", content = @Content)
    })
    ResponseEntity<UserDetailsResponse> getMe(@Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser currentUser);

    @Operation(summary = "Listar usuários cadastrados", description = "Lista todas as líderes de cozinha e gestoras cadastradas no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários recuperada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso restrito a Gestoras", content = @Content)
    })
    ResponseEntity<List<UserDetailsResponse>> listAll();
}