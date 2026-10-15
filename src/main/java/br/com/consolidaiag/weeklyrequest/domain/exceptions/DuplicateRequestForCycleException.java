package br.com.consolidaiag.weeklyrequest.domain.exceptions;

import br.com.consolidaiag.shared.domain.exceptions.ConflictException;
import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;

import java.util.UUID;

/**
 * Lançada quando um serviço tenta submeter mais de uma solicitação no mesmo ciclo semanal (RN-02.4).
 */
public final class DuplicateRequestForCycleException extends ConflictException {

    private final ServiceCode serviceCode;
    private final UUID cycleId;

    public DuplicateRequestForCycleException(ServiceCode serviceCode, UUID cycleId) {
        super(String.format("Service '%s' has already submitted a request for cycle %s (RN-02.4).", serviceCode, cycleId));
        this.serviceCode = serviceCode;
        this.cycleId = cycleId;
    }

    public ServiceCode getServiceCode() {
        return serviceCode;
    }

    public UUID getCycleId() {
        return cycleId;
    }
}