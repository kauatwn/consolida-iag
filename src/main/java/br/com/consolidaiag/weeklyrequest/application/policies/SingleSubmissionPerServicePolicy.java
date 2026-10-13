package br.com.consolidaiag.weeklyrequest.application.policies;

import br.com.consolidaiag.weeklyrequest.application.ports.out.WeeklyRequestRepositoryPort;
import br.com.consolidaiag.weeklyrequest.domain.WeeklyRequest;
import br.com.consolidaiag.weeklyrequest.domain.exceptions.DuplicateRequestForCycleException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Valida a regra RN-02.4: Exatamente uma solicitação de compras por serviço é permitida para cada ciclo semanal.
 * Assina apenas o contrato de Submissão, pois a validação de duplicidade não se aplica durante a edição de um pedido.
 */
@Component
@Order(1)
public class SingleSubmissionPerServicePolicy implements SubmitWeeklyRequestPolicy {

    private final WeeklyRequestRepositoryPort repository;

    public SingleSubmissionPerServicePolicy(WeeklyRequestRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void validate(WeeklyRequest request) {
        if (request == null) {
            return;
        }

        if (repository.existsByServiceCodeAndCycleId(request.getServiceCode(), request.getCycleId())) {
            throw new DuplicateRequestForCycleException(request.getServiceCode(), request.getCycleId());
        }
    }
}