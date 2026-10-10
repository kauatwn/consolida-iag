package br.com.consolidaiag.weeklyrequest.adapters.out.persistence;

import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "requests",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_service_cycle", columnNames = {"service_code", "cycle_id"})
        }
)
public class WeeklyRequestJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_code", nullable = false)
    private ServiceCode serviceCode;

    @Column(name = "cycle_id", nullable = false)
    private Long cycleId;

    @Column(name = "observation")
    private String observation;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Relacionamento bidirecional delegando o controle para a classe filha
    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequestItemJpaEntity> items = new ArrayList<>();

    public WeeklyRequestJpaEntity() {
    }

    public WeeklyRequestJpaEntity(Long id, ServiceCode serviceCode, Long cycleId, String observation, LocalDateTime createdAt, List<RequestItemJpaEntity> items) {
        this.id = id;
        this.serviceCode = serviceCode;
        this.cycleId = cycleId;
        this.observation = observation;
        this.createdAt = createdAt;
        this.items = items != null ? items : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceCode getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(ServiceCode serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Long getCycleId() {
        return cycleId;
    }

    public void setCycleId(Long cycleId) {
        this.cycleId = cycleId;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<RequestItemJpaEntity> getItems() {
        return items;
    }

    public void setItems(List<RequestItemJpaEntity> items) {
        this.items = items != null ? items : new ArrayList<>();
    }
}