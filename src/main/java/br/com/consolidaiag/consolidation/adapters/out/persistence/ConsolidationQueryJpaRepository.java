package br.com.consolidaiag.consolidation.adapters.out.persistence;

import br.com.consolidaiag.consolidation.application.dtos.response.ConsolidatedItemResponse;
import br.com.consolidaiag.weeklyrequest.adapters.out.persistence.WeeklyRequestJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ConsolidationQueryJpaRepository extends JpaRepository<WeeklyRequestJpaEntity, UUID> {

    @Query("""
                SELECT new br.com.consolidaiag.consolidation.application.dtos.response.ConsolidatedItemResponse(
                    p.id,
                    p.name,
                    CAST(p.unitMeasure AS string),
                    SUM(i.quantity)
                )
                FROM WeeklyRequestJpaEntity r
                JOIN r.items i
                JOIN ProductJpaEntity p ON i.productId = p.id
                WHERE r.cycleId = :cycleId
                  AND (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', CAST(:search AS string), '%')))
                GROUP BY p.id, p.name, p.unitMeasure
                ORDER BY p.name ASC
            """)
    List<ConsolidatedItemResponse> findConsolidatedItemsByCycleId(
            @Param("cycleId") UUID cycleId,
            @Param("search") String search
    );
}