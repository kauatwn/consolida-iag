package br.com.consolidaiag.cycle.adapters.out.persistence;

import br.com.consolidaiag.cycle.domain.CycleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WeeklyCycleJpaRepository extends JpaRepository<WeeklyCycleJpaEntity, UUID> {

    // O Spring gera o SQL automaticamente: SELECT COUNT(...) > 0 FROM cycles WHERE status = ?
    boolean existsByStatus(CycleStatus status);

    // O Spring gera: SELECT * FROM cycles WHERE status = ? LIMIT 1
    Optional<WeeklyCycleJpaEntity> findByStatus(CycleStatus status);
}