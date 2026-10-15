package br.com.consolidaiag.weeklyrequest.domain.exceptions;

import br.com.consolidaiag.shared.domain.exceptions.ResourceNotFoundException;

/**
 * Lançada quando não há nenhum ciclo semanal aberto no momento (RN-02.5).
 */
public final class ActiveCycleNotFoundException extends ResourceNotFoundException {

    public ActiveCycleNotFoundException() {
        super("No active open purchasing cycle found at this time (RN-02.5).");
    }
}