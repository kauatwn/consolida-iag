package br.com.consolidaiag.shared.adapters.out.persistence;

import br.com.consolidaiag.shared.adapters.in.security.AuthenticatedUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null
                    && authentication.isAuthenticated()
                    && authentication.getPrincipal() instanceof AuthenticatedUser user
                    && user.email() != null
                    && !user.email().isBlank()) {
                return Optional.of(user.email());
            }
            return Optional.of("SYSTEM");
        };
    }
}