package br.com.consolidaiag.weeklyrequest.application.policies;

import br.com.consolidaiag.weeklyrequest.domain.WeeklyRequest;

/**
 * Interface base selada para políticas de validação (Specification Pattern) de solicitações semanais.
 * Somente as interfaces de Submissão e Edição têm permissão para estendê-la, garantindo
 * que as regras sejam aplicadas estritamente nos contextos corretos.
 */
public sealed interface WeeklyRequestPolicy permits SubmitWeeklyRequestPolicy, EditWeeklyRequestPolicy {

    /**
     * Valida a solicitação semanal contra uma regra ou política de negócio específica.
     *
     * @param request a solicitação a ser validada
     * @throws RuntimeException se a regra de negócio for violada
     */
    void validate(WeeklyRequest request);
}