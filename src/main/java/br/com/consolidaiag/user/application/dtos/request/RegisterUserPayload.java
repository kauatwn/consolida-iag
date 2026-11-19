package br.com.consolidaiag.user.application.dtos.request;

import br.com.consolidaiag.user.domain.UserRole;
import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro de um usuário (Líder de Cozinha ou Gestora)")
public record RegisterUserPayload(

        @Schema(description = "UID do usuário no Firebase Authentication", example = "5XpA9rY2bCdEfGhIjKlMnOpQrStU")
        String firebaseUid,

        @Schema(description = "E-mail de acesso do usuário", example = "lider.cei@anchieta.org.br")
        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "Formato de e-mail inválido.")
        String email,

        @Schema(description = "Nome completo do usuário", example = "Maria Silva")
        @NotBlank(message = "O nome é obrigatório.")
        @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
        String name,

        @Schema(description = "Perfil de acesso do usuário", example = "KITCHEN_LEADER")
        @NotNull(message = "O perfil (role) é obrigatório.")
        UserRole role,

        @Schema(description = "Código do serviço para Líder de Cozinha (CEI, CCA, CJ)", example = "CEI")
        ServiceCode serviceCode
) {
}
