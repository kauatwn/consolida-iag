package br.com.consolidaiag.weeklycycle.application.policies;

import br.com.consolidaiag.weeklycycle.domain.WeeklyCycle;

/**
 * Contrato base para todas as políticas de negócio relacionadas ao Ciclo Semanal.
 * É uma interface selada: apenas as interfaces de Abertura e Fechamento têm permissão para estendê-la,
 * garantindo a separação estrita de contextos.
 */
public sealed interface WeeklyCyclePolicy permits OpenWeeklyCyclePolicy, CloseWeeklyCyclePolicy {
    void validate(WeeklyCycle cycle);
}