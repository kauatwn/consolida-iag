package br.com.consolidaiag.weeklyrequest.domain.exceptions;

import br.com.consolidaiag.shared.domain.exceptions.BusinessRuleException;

import java.util.UUID;

/**
 * Lançada quando há tentativa de submeter, alterar ou remover solicitações em um ciclo já fechado (RN-02.5).
 */
public final class ClosedCycleImmutableException extends BusinessRuleException {

    private final UUID cycleId;

    public ClosedCycleImmutableException(UUID cycleId) {
        super(String.format("Cycle %s is closed and strictly immutable. Operations are not permitted (RN-02.5).", cycleId));
        this.cycleId = cycleId;
    }

    public UUID getCycleId() {
        return cycleId;
    }
}