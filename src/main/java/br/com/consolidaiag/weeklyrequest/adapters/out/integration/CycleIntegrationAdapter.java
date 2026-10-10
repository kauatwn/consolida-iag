package br.com.consolidaiag.weeklyrequest.adapters.out.integration;

import br.com.consolidaiag.weeklyrequest.application.ports.out.ActiveCyclePort;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CycleIntegrationAdapter implements ActiveCyclePort {

    @Override
    public Optional<UUID> getActiveCycleId() {
        /*
         * TODO: IMPLEMENTAÇÃO MOCKADA TEMPORÁRIA.
         * Finge que existe um ciclo semanal aberto com UUID fixo.
         * Futuramente, isso chamará o subdomínio 'cycle'.
         */
        return Optional.of(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }
}