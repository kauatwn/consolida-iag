package br.com.consolidaiag.weeklyrequest.application.policies;

import br.com.consolidaiag.weeklyrequest.domain.WeeklyRequest;

/**
 * Interface base para políticas de validação (Specification Pattern) de solicitações semanais.
 */
public interface WeeklyRequestPolicy {

    /**
     * Valida a solicitação semanal contra uma regra ou política de negócio específica.
     *
     * @param request a solicitação a ser validada
     * @throws RuntimeException se a regra de negócio for violada
     */
    void validate(WeeklyRequest request);
}