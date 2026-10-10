package br.com.consolidaiag.weeklyrequest.adapters.out.persistence;

import br.com.consolidaiag.weeklyrequest.domain.WeeklyRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeeklyRequestPersistenceMapper {

    public WeeklyRequestJpaEntity toJpaEntity(WeeklyRequest domain) {
        if (domain == null) {
            return null;
        }

        WeeklyRequestJpaEntity entity = new WeeklyRequestJpaEntity();
        entity.setId(domain.getId());
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

        // Usa a Factory de reconstituição, preservando o ID e a data exata do banco
        WeeklyRequest domain = WeeklyRequest.reconstitute(
                entity.getId(),
                entity.getServiceCode(),
                entity.getCycleId(),
                entity.getObservation(),
                entity.getCreatedAt()
        );

        if (entity.getItems() != null) {
            for (RequestItemJpaEntity itemEntity : entity.getItems()) {
                domain.addItem(itemEntity.getProductId(), itemEntity.getQuantity());
            }
        }

        return domain;
    }
}