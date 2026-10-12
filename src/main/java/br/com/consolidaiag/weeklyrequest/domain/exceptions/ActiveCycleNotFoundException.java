package br.com.consolidaiag.weeklyrequest.domain.exceptions;

import br.com.consolidaiag.shared.DomainException;

/**
 * Lançada quando não há nenhum ciclo semanal aberto no momento (RN-02.5).
 */
public final class ActiveCycleNotFoundException extends DomainException {

    public ActiveCycleNotFoundException() {
        super("No active open purchasing cycle found at this time (RN-02.5).");
    }

    public ActiveCycleNotFoundException(String message) {
        super(message);
    }
}