package br.com.consolidaiag.weeklyrequest.adapters.out.integration;

import br.com.consolidaiag.weeklyrequest.application.ports.out.ActiveCyclePort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CycleIntegrationAdapter implements ActiveCyclePort {

    @Override
    public Optional<Long> getActiveCycleId() {
        /*
         * TODO: IMPLEMENTAÇÃO MOCKADA TEMPORÁRIA.
         * Finge que o ciclo semanal número 1 está aberto.
         * Futuramente, isso chamará o subdomínio 'cycle'.
         */
        return Optional.of(1L);
    }
}