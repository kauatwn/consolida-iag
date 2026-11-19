package br.com.consolidaiag.weeklycycle.application.policies;

/**
 * Contrato para políticas que devem ser executadas exclusivamente durante o FECHAMENTO de um ciclo ativo.
 */
public non-sealed interface CloseWeeklyCyclePolicy extends WeeklyCyclePolicy {
}