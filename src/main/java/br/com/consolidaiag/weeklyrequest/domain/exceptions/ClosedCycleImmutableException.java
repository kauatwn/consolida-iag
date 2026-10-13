package br.com.consolidaiag.weeklyrequest.domain.exceptions;

import br.com.consolidaiag.shared.DomainException;

import java.util.UUID;

/**
 * Lançada quando há tentativa de submeter, alterar ou remover solicitações em um ciclo já fechado (RN-02.5).
 */
public final class ClosedCycleImmutableException extends DomainException {

    private final UUID cycleId;

    public ClosedCycleImmutableException(UUID cycleId) {
        super(String.format("Cycle %s is closed and strictly immutable. Operations are not permitted (RN-02.5).", cycleId));
        this.cycleId = cycleId;
    }

    public ClosedCycleImmutableException(String message) {
        super(message);
        this.cycleId = null;
    }

    public UUID getCycleId() {
        return cycleId;
    }
}