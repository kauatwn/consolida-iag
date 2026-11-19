package br.com.consolidaiag.weeklyrequest.adapters.out.integration;

import br.com.consolidaiag.weeklycycle.application.ports.in.FetchActiveCycleUseCase;
import br.com.consolidaiag.weeklyrequest.application.ports.out.FetchActiveCyclePort;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CycleIntegrationAdapter implements FetchActiveCyclePort {

    private final FetchActiveCycleUseCase fetchActiveCycleUseCase;

    public CycleIntegrationAdapter(FetchActiveCycleUseCase fetchActiveCycleUseCase) {
        this.fetchActiveCycleUseCase = fetchActiveCycleUseCase;
    }

    @Override
    public Optional<UUID> getActiveCycleId() {
        return fetchActiveCycleUseCase.execute();
    }
}