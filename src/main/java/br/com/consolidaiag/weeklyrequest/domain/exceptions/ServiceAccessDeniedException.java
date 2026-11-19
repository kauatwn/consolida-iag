package br.com.consolidaiag.weeklyrequest.domain.exceptions;

import br.com.consolidaiag.shared.domain.exceptions.ForbiddenException;
import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;

public class ServiceAccessDeniedException extends ForbiddenException {

    public ServiceAccessDeniedException(ServiceCode targetService) {
        super("Acesso negado: Você não tem permissão para acessar ou submeter solicitações do serviço " + targetService + ".");
    }
}
