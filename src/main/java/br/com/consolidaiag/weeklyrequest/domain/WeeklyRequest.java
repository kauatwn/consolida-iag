package br.com.consolidaiag.weeklyrequest.domain;

import br.com.consolidaiag.weeklyrequest.domain.exceptions.DuplicateRequestItemException;
import br.com.consolidaiag.weeklyrequest.domain.exceptions.EmptyRequestItemsException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public final class WeeklyRequest {

    private final WeeklyRequestId id;
    private final ServiceCode serviceCode;
    private final UUID cycleId;
    private String observation;
    private final List<RequestItem> items;
    private final LocalDateTime createdAt;

    // Construtor privado: Ninguém fora da classe pode instanciar diretamente
    private WeeklyRequest(WeeklyRequestId id, ServiceCode serviceCode, UUID cycleId, String observation, LocalDateTime createdAt, List<RequestItem> items) {
        this.id = id;
        this.serviceCode = Objects.requireNonNull(serviceCode, "Service code cannot be null.");
        this.cycleId = Objects.requireNonNull(cycleId, "Cycle ID cannot be null.");
        this.observation = observation;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.items = new ArrayList<>();
        Objects.requireNonNull(items, "Items list cannot be null.");

        if (items.isEmpty()) {
            throw new EmptyRequestItemsException();
        }

        // Popula os itens garantindo quantity > 0 e sem duplicidade
        items.forEach(item -> this.addItem(item.productId(), item.quantity()));
    }

    // Factory 1: Para a Líder de Cozinha criar uma NOVA solicitação
    public static WeeklyRequest create(ServiceCode serviceCode, UUID cycleId, String observation, List<RequestItem> items) {
        return new WeeklyRequest(WeeklyRequestId.generate(), serviceCode, cycleId, observation, LocalDateTime.now(), items);
    }

    // Factory 2: Para o JPA remontar o objeto vindo do banco de dados
    public static WeeklyRequest reconstitute(WeeklyRequestId id, ServiceCode serviceCode, UUID cycleId, String observation, LocalDateTime createdAt, List<RequestItem> items) {
        return new WeeklyRequest(id, serviceCode, cycleId, observation, createdAt, items);
    }

    public void addItem(UUID productId, BigDecimal quantity) {
        // Enforce uniqueness constraint (uq_solicitacao_produto)
        boolean alreadyExists = this.items.stream()
                .anyMatch(item -> item.productId().equals(productId));

        if (alreadyExists) {
            throw new DuplicateRequestItemException(productId);
        }

        this.items.add(new RequestItem(productId, quantity));
    }

    public void update(String observation, List<RequestItem> items) {
        this.observation = observation;
        Objects.requireNonNull(items, "New items list cannot be null.");

        if (items.isEmpty()) {
            throw new EmptyRequestItemsException();
        }

        this.items.clear();

        items.forEach(item -> this.addItem(item.productId(), item.quantity()));
    }

    public WeeklyRequestId getId() {
        return id;
    }

    public ServiceCode getServiceCode() {
        return serviceCode;
    }

    public UUID getCycleId() {
        return cycleId;
    }

    public String getObservation() {
        return observation;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<RequestItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}