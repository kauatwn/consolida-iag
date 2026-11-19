package br.com.consolidaiag.user.application.dtos.response;

import br.com.consolidaiag.user.domain.User;
import br.com.consolidaiag.user.domain.UserRole;
import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Detalhes cadastrais e perfil de acesso do usuário")
public record UserDetailsResponse(

        @Schema(description = "Identificador único do usuário", example = "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d")
        UUID id,

        @Schema(description = "UID do usuário no Firebase Authentication", example = "5XpA9rY2bCdEfGhIjKlMnOpQrStU")
        String firebaseUid,

        @Schema(description = "E-mail de acesso institucional", example = "lider.cei@anchieta.org.br")
        String email,

        @Schema(description = "Nome completo do usuário", example = "Maria Silva")
        String name,

        @Schema(description = "Perfil de acesso", example = "KITCHEN_LEADER")
        UserRole role,

        @Schema(description = "Serviço fiscal vinculado (exclusivo para líderes)", example = "CEI")
        ServiceCode serviceCode,

        @Schema(description = "Indica se o usuário está ativo no sistema", example = "true")
        boolean active
) {
    public static UserDetailsResponse fromDomain(User user) {
        if (user == null) {
            return null;
        }

        return new UserDetailsResponse(
                user.getId().value(),
                user.getFirebaseUid(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getServiceCode(),
                user.isActive()
        );
    }
}
