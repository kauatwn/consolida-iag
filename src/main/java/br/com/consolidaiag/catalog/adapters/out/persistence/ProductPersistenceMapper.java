package br.com.consolidaiag.catalog.adapters.out.persistence;

import br.com.consolidaiag.catalog.domain.Product;
import br.com.consolidaiag.catalog.domain.ProductId;
import br.com.consolidaiag.catalog.domain.ProductStatus;
import org.springframework.stereotype.Component;

@Component
public class ProductPersistenceMapper {

    public ProductJpaEntity toJpaEntity(Product domain) {
        if (domain == null) {
            return null;
        }
        return new ProductJpaEntity(
                domain.getId().value(),
                domain.getName(),
                domain.getUnitMeasure(),
                domain.getProductStatus()
        );
    }

    public Product toDomain(ProductJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        // Usa a Factory estática do domínio para reconstituir o objeto sem corrompê-lo
        return Product.reconstitute(
                new ProductId(entity.getId()),
                entity.getName(),
                entity.getUnitMeasure(),
                entity.getProductStatus()
        );
    }
}