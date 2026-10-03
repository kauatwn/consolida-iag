package br.com.consolidaiag.weeklyrequest.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeeklyRequest {
    private Long id; // Null before persistence
    private final ServiceCode serviceCode;
    private final Long cycleId;
    private final String observation;
    private final List<RequestItem> items;
    private final LocalDateTime createdAt;

    // Construtor privado: Ninguém fora da classe pode instanciar diretamente
    private WeeklyRequest(Long id, ServiceCode serviceCode, Long cycleId, String observation, LocalDateTime createdAt) {
        this.id = id;
        this.serviceCode = serviceCode;
        this.cycleId = cycleId;
        this.observation = observation;
        this.createdAt = createdAt;
        this.items = new ArrayList<>();
    }

    // Factory 1: Para a Líder de Cozinha criar uma NOVA solicitação
    public static WeeklyRequest create(ServiceCode serviceCode, Long cycleId, String observation) {
        return new WeeklyRequest(null, serviceCode, cycleId, observation, LocalDateTime.now());
    }

    // Factory 2: Para o JPA remontar o objeto vindo do banco de dados
    public static WeeklyRequest reconstitute(Long id, ServiceCode serviceCode, Long cycleId, String observation, LocalDateTime createdAt) {
        return new WeeklyRequest(id, serviceCode, cycleId, observation, createdAt);
    }

    public void addItem(Long productId, BigDecimal quantity) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null.");
        }

        // Enforcing RN-02.2 inside the domain
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        // Enforce uniqueness constraint (uq_solicitacao_produto)
        boolean alreadyExists = this.items.stream()
            .anyMatch(item -> item.productId().equals(productId));
        
        if (alreadyExists) {
            throw new IllegalArgumentException("Product with ID " + productId + " already added to this request.");
        }

        this.items.add(new RequestItem(productId, quantity));
    }

    public Long getId() {
        return id;
    }

    public ServiceCode getServiceCode() {
        return serviceCode;
    }

    public Long getCycleId() {
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