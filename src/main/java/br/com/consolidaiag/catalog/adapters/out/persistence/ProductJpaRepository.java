package br.com.consolidaiag.catalog.adapters.out.persistence;

import br.com.consolidaiag.catalog.domain.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, UUID> {

    // Método gerado automaticamente pelo Spring para checar a duplicidade
    boolean existsByNameIgnoreCase(String name);

    @Query("SELECT COUNT(p) FROM ProductJpaEntity p WHERE p.id IN :ids AND p.status = 'OPEN'")
    long countByIdInAndActiveTrue(@Param("ids") List<UUID> ids);
}