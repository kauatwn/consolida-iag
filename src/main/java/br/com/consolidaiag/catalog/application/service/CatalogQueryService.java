package br.com.consolidaiag.catalog.application.service;

import br.com.consolidaiag.catalog.application.ports.in.CatalogQueryPort;
import br.com.consolidaiag.catalog.application.ports.out.ProductRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CatalogQueryService implements CatalogQueryPort {

    private final ProductRepositoryPort productRepositoryPort;

    public CatalogQueryService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    public boolean verifyProductsStatus(List<UUID> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            return true;
        }

        // Remove duplicados da lista (programação defensiva)
        List<UUID> uniqueIds = productIds.stream().distinct().toList();

        // Consulta a base de dados real
        long activeCount = productRepositoryPort.countActiveProductsByIds(uniqueIds);

        // Se a quantidade de produtos ativos encontrados for igual à quantidade de IDs únicos solicitados, está tudo certo.
        return activeCount == uniqueIds.size();
    }
}