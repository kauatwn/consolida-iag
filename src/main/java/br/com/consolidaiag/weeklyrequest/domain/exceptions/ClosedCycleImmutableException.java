package br.com.consolidaiag.weeklyrequest.domain.exceptions;

import br.com.consolidaiag.shared.DomainException;

/**
 * Lançada quando há tentativa de submeter, alterar ou remover solicitações em um ciclo já fechado (RN-02.5).
 */
public final class ClosedCycleImmutableException extends DomainException {

    private final Long cycleId;

    public ClosedCycleImmutableException(Long cycleId) {
        super(String.format("Cycle %d is closed and strictly immutable. Operations are not permitted (RN-02.5).", cycleId));
        this.cycleId = cycleId;
    }

    public ClosedCycleImmutableException(String message) {
        super(message);
        this.cycleId = null;
    }

    public Long getCycleId() {
        return cycleId;
    }
}