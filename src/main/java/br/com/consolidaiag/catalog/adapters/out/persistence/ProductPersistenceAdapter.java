package br.com.consolidaiag.catalog.adapters.out.persistence;

import br.com.consolidaiag.catalog.application.ports.out.ProductRepositoryPort;
import br.com.consolidaiag.catalog.domain.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProductPersistenceAdapter implements ProductRepositoryPort {

    private final ProductJpaRepository jpaRepository;
    private final ProductPersistenceMapper mapper;

    public ProductPersistenceAdapter(ProductJpaRepository jpaRepository, ProductPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Product save(Product product) {
        // 1. Traduz do Domínio para o JPA
        ProductJpaEntity entity = mapper.toJpaEntity(product);

        // 2. Salva no banco de dados usando o Spring Data
        ProductJpaEntity savedEntity = jpaRepository.save(entity);

        // 3. Traduz de volta para o Domínio puro
        return mapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByName(String name) {
        // Delega para a busca ignorando maiúsculas/minúsculas do repositório
        return jpaRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public long countActiveProductsByIds(List<UUID> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            return 0;
        }

        return jpaRepository.countByIdInAndActiveTrue(productIds);
    }
}