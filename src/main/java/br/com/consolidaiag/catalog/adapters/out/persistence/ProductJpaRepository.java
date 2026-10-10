package br.com.consolidaiag.catalog.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {

    // Método gerado automaticamente pelo Spring para checar a duplicidade
    boolean existsByNameIgnoreCase(String name);

    // Conta quantos produtos da lista estão com active = true
    long countByIdInAndActiveTrue(List<Long> ids);
}