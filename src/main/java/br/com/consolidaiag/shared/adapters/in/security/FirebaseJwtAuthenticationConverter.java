package br.com.consolidaiag.shared.adapters.in.security;

import br.com.consolidaiag.user.application.ports.out.UserRepositoryPort;
import br.com.consolidaiag.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FirebaseJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final Logger log = LoggerFactory.getLogger(FirebaseJwtAuthenticationConverter.class);

    private final UserRepositoryPort userRepository;

    public FirebaseJwtAuthenticationConverter(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String uid = jwt.getSubject();
        String email = jwt.getClaimAsString("email");

        // Busca o usuário pelo e-mail. Caso não seja encontrado, tenta localizá-lo pelo UID do Firebase.
        Optional<User> userOpt = Optional.empty();

        if (email != null && !email.isBlank()) {
            userOpt = userRepository.findByEmail(email);
        }

        if (userOpt.isEmpty() && uid != null && !uid.isBlank()) {
            userOpt = userRepository.findByFirebaseUid(uid);
        }

        // Caso o usuário não esteja cadastrado, mantém a autenticação com os dados disponíveis no JWT.
        if (userOpt.isEmpty()) {
            AuthenticatedUser authenticatedUser = new AuthenticatedUser(uid, email, null, null);

            log.debug("User not found in database: uid={}, email={}", uid, email);

            return new UsernamePasswordAuthenticationToken(authenticatedUser, jwt, List.of());
        }

        User dbUser = userOpt.get();

        // Utiliza o UID armazenado no banco ou o UID recebido pelo Firebase, caso não esteja cadastrado.
        String firebaseUid = dbUser.getFirebaseUid();

        if (firebaseUid == null) {
            firebaseUid = uid;
        }

        // Recupera o perfil de acesso do usuário, caso esteja definido.
        String role = null;

        if (dbUser.getRole() != null) {
            role = dbUser.getRole().name();
        }

        AuthenticatedUser authenticatedUser = new AuthenticatedUser(
                firebaseUid,
                dbUser.getEmail(),
                dbUser.getServiceCode(),
                role
        );

        log.debug("Authenticated user: email={}, service={}, role={}",
                authenticatedUser.email(),
                authenticatedUser.serviceCode(),
                authenticatedUser.role());

        // Caso o usuário não possua perfil de acesso, retorna a autenticação sem autoridades.
        if (role == null || role.isBlank()) {
            return new UsernamePasswordAuthenticationToken(authenticatedUser, jwt, List.of());
        }

        // Converte o perfil do usuário em uma autoridade reconhecida pelo Spring Security.
        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())
        );

        return new UsernamePasswordAuthenticationToken(authenticatedUser, jwt, authorities);
    }
}