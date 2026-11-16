package br.com.consolidaiag.shared.adapters.out.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        // Quando adicionar o Spring Security, substitua pelo retorno do SecurityContextHolder:
        // return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
        //         .filter(Authentication::isAuthenticated)
        //         .map(Authentication::getName);
        return () -> Optional.of("SYSTEM");
    }
}