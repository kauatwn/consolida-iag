package br.com.consolidaiag.catalog.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, UUID> {

    // Método gerado automaticamente pelo Spring para checar a duplicidade
    boolean existsByNameIgnoreCase(String name);

    // Conta quantos produtos da lista estão com active = true
    long countByIdInAndActiveTrue(List<UUID> ids);
}