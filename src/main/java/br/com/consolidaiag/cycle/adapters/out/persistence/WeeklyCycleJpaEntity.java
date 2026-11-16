package br.com.consolidaiag.cycle.adapters.out.persistence;

import br.com.consolidaiag.cycle.domain.CycleStatus;
import br.com.consolidaiag.shared.adapters.out.persistence.AuditableJpaEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "cycles")
public class WeeklyCycleJpaEntity extends AuditableJpaEntity {

    @Id
    private UUID id;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CycleStatus status;

    public WeeklyCycleJpaEntity() {
    }

    public WeeklyCycleJpaEntity(UUID id, LocalDate startDate, LocalDate endDate, CycleStatus status) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public CycleStatus getStatus() {
        return status;
    }

    public void setStatus(CycleStatus status) {
        this.status = status;
    }
}