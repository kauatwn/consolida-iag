package br.com.consolidaiag.cycle.adapters.out.persistence;

import br.com.consolidaiag.cycle.domain.WeeklyCycle;
import br.com.consolidaiag.cycle.domain.WeeklyCycleId;
import org.springframework.stereotype.Component;

@Component
public class WeeklyCyclePersistenceMapper {

    public WeeklyCycleJpaEntity toJpaEntity(WeeklyCycle domain) {
        if (domain == null) {
            return null;
        }

        return new WeeklyCycleJpaEntity(
                domain.getId().value(),
                domain.getStartDate(),
                domain.getEndDate(),
                domain.getStatus()
        );
    }

    public WeeklyCycle toDomain(WeeklyCycleJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return WeeklyCycle.reconstitute(
                new WeeklyCycleId(entity.getId()),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getStatus()
        );
    }
}