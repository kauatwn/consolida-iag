package br.com.consolidaiag.cycle.application.policies;

import br.com.consolidaiag.cycle.application.ports.out.WeeklyCycleRepositoryPort;
import br.com.consolidaiag.cycle.domain.WeeklyCycle;
import br.com.consolidaiag.cycle.domain.exceptions.ActiveCycleAlreadyExistsException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Valida a regra RN-01: Apenas um ciclo pode estar aberto por vez.
 * Esta política impede a criação de janelas de compras sobrepostas.
 */
@Component
@Order(1)
public class SingleActiveCyclePolicy implements OpenWeeklyCyclePolicy {

    private final WeeklyCycleRepositoryPort repository;

    public SingleActiveCyclePolicy(WeeklyCycleRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void validate(WeeklyCycle cycle) {
        if (cycle == null) {
            return;
        }

        if (repository.hasActiveCycle()) {
            throw new ActiveCycleAlreadyExistsException();
        }
    }
}