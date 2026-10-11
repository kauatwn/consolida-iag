package br.com.consolidaiag.cycle.application.policies;

import br.com.consolidaiag.cycle.domain.WeeklyCycle;

public interface OpenCyclePolicy {
    void validate(WeeklyCycle cycle);
}