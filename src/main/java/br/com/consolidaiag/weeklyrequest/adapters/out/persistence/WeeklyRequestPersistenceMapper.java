package br.com.consolidaiag.weeklyrequest.adapters.out.persistence;

import br.com.consolidaiag.weeklyrequest.domain.RequestItem;
import br.com.consolidaiag.weeklyrequest.domain.WeeklyRequest;
import br.com.consolidaiag.weeklyrequest.domain.WeeklyRequestId;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class WeeklyRequestPersistenceMapper {

    // 1. Para CRIAÇÃO (INSERT)
    public WeeklyRequestJpaEntity toJpaEntity(WeeklyRequest request) {
        if (request == null) return null;

        WeeklyRequestJpaEntity entity = new WeeklyRequestJpaEntity();
        entity.setId(request.getId().value());
        entity.setServiceCode(request.getServiceCode());
        entity.setCycleId(request.getCycleId());
        entity.setObservation(request.getObservation());
        entity.setCreatedAt(request.getCreatedAt());

        // Amarração bidirecional diretamente no momento da extração
        entity.setItems(request.getItems().stream()
                .map(item -> {
                    RequestItemJpaEntity itemEntity = new RequestItemJpaEntity(item.productId(), item.quantity());
                    itemEntity.setRequest(entity);

                    return itemEntity;
                })
                .toList());

        return entity;
    }

    // 2. Para EDIÇÃO (Smart Merge O(1) usando Map/Set)
    public void updateJpaEntity(WeeklyRequest request, WeeklyRequestJpaEntity entity) {
        if (request == null || entity == null) return;

        entity.setObservation(request.getObservation());

        // Mapeia os itens do banco por productId -> O(1)
        Map<UUID, RequestItemJpaEntity> existingItems = entity.getItems()
                .stream()
                .collect(Collectors.toMap(
                        RequestItemJpaEntity::getProductId,
                        Function.identity()
                ));

        Set<UUID> requestedProductIds = request.getItems().stream()
                .map(RequestItem::productId)
                .collect(Collectors.toSet());

        entity.getItems().removeIf(item -> !requestedProductIds.contains(item.getProductId()));

        request.getItems().forEach(item -> {
            RequestItemJpaEntity existing = existingItems.get(item.productId());

            if (existing != null) {
                existing.setQuantity(item.quantity());
                return;
            }

            RequestItemJpaEntity newItem = new RequestItemJpaEntity(item.productId(), item.quantity());
            newItem.setRequest(entity);
            entity.getItems().add(newItem);
        });
    }

    // 3. Para RECONSTITUIÇÃO (Leitura do Banco -> Domínio)
    public WeeklyRequest toDomain(WeeklyRequestJpaEntity entity) {
        if (entity == null) return null;

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