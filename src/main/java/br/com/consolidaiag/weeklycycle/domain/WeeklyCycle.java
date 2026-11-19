package br.com.consolidaiag.weeklycycle.domain;

import br.com.consolidaiag.weeklycycle.domain.exceptions.CycleAlreadyClosedException;
import br.com.consolidaiag.weeklycycle.domain.exceptions.InvalidCycleDateException;

import java.time.LocalDate;
import java.util.Objects;

public final class WeeklyCycle {

    private final WeeklyCycleId id;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private CycleStatus status;

    private WeeklyCycle(WeeklyCycleId id, LocalDate startDate, LocalDate endDate, CycleStatus status) {
        this.id = Objects.requireNonNull(id, "Cycle ID cannot be null.");
        this.startDate = Objects.requireNonNull(startDate, "Start date cannot be null.");
        this.endDate = Objects.requireNonNull(endDate, "End date cannot be null.");
        this.status = Objects.requireNonNull(status, "Status cannot be null.");

        if (endDate.isBefore(startDate)) {
            throw new InvalidCycleDateException();
        }
    }

    // Factory: A Gestora cria um novo ciclo (ele já nasce ABERTO)
    public static WeeklyCycle openNew(LocalDate startDate, LocalDate endDate) {
        return new WeeklyCycle(WeeklyCycleId.generate(), startDate, endDate, CycleStatus.OPEN);
    }

    // Factory: Para o JPA reconstituir o objeto do banco
    public static WeeklyCycle reconstitute(WeeklyCycleId id, LocalDate startDate, LocalDate endDate, CycleStatus status) {
        return new WeeklyCycle(id, startDate, endDate, status);
    }

    // Ação de negócio: Gestora fecha o ciclo para enviar os relatórios
    public void close() {
        if (status == CycleStatus.CLOSED) {
            throw new CycleAlreadyClosedException();
        }

        status = CycleStatus.CLOSED;
    }

    public WeeklyCycleId getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public CycleStatus getStatus() {
        return status;
    }
}