package br.com.consolidaiag.weeklyrequest.application.policies;

/**
 * Contrato para políticas que devem ser executadas exclusivamente durante a CRIAÇÃO
 * (primeira submissão) de uma nova solicitação semanal.
 */
public non-sealed interface SubmitWeeklyRequestPolicy extends WeeklyRequestPolicy {
}