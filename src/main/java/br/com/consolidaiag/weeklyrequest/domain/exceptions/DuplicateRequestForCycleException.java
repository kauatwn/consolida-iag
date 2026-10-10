package br.com.consolidaiag.weeklyrequest.domain.exceptions;

import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;

/**
 * Lançada quando um serviço tenta submeter mais de uma solicitação no mesmo ciclo semanal (RN-02.4).
 */
public class DuplicateRequestForCycleException extends RuntimeException {

    private final ServiceCode serviceCode;
    private final Long cycleId;

    public DuplicateRequestForCycleException(ServiceCode serviceCode, Long cycleId) {
        super(String.format("Service '%s' has already submitted a request for cycle %d (RN-02.4).", serviceCode, cycleId));
        this.serviceCode = serviceCode;
        this.cycleId = cycleId;
    }

    public DuplicateRequestForCycleException(String message) {
        super(message);
        this.serviceCode = null;
        this.cycleId = null;
    }

    public ServiceCode getServiceCode() {
        return serviceCode;
    }

    public Long getCycleId() {
        return cycleId;
    }
}
