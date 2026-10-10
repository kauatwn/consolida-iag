package br.com.consolidaiag.weeklyrequest.adapters.out.persistence;

import br.com.consolidaiag.weeklyrequest.domain.RequestItem;
import br.com.consolidaiag.weeklyrequest.domain.WeeklyRequest;
import br.com.consolidaiag.weeklyrequest.domain.WeeklyRequestId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class WeeklyRequestPersistenceMapper {

    public WeeklyRequestJpaEntity toJpaEntity(WeeklyRequest domain) {
        if (domain == null) {
            return null;
        }

        WeeklyRequestJpaEntity entity = new WeeklyRequestJpaEntity();
        entity.setId(domain.getId().value());
        entity.setServiceCode(domain.getServiceCode());
        entity.setCycleId(domain.getCycleId());
        entity.setObservation(domain.getObservation());
        entity.setCreatedAt(domain.getCreatedAt());

        // Amarração bidirecional diretamente no momento da extração
        List<RequestItemJpaEntity> itemEntities = domain.getItems().stream()
                .map(item -> {
                    RequestItemJpaEntity itemEntity = new RequestItemJpaEntity(item.productId(), item.quantity());
                    itemEntity.setRequest(entity); // Protege o Hibernate de tentar inserir NULL na chave estrangeira
                    return itemEntity;
                })
                .toList();

        entity.setItems(itemEntities);
        return entity;
    }

    public WeeklyRequest toDomain(WeeklyRequestJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        List<RequestItem> items = Optional.ofNullable(entity.getItems())
                .orElseGet(List::of)
                .stream()
                .map(item -> new RequestItem(item.getProductId(), item.getQuantity()))
                .toList();

        return WeeklyRequest.reconstitute(
                new WeeklyRequestId(entity.getId()),
                entity.getServiceCode(),
                entity.getCycleId(),
                entity.getObservation(),
                entity.getCreatedAt(),
                items
        );
    }
}