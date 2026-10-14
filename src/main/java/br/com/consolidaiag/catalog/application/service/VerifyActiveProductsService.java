package br.com.consolidaiag.catalog.application.service;

import br.com.consolidaiag.catalog.application.ports.in.VerifyActiveProductsUseCase;
import br.com.consolidaiag.catalog.application.ports.out.ProductRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class VerifyActiveProductsService implements VerifyActiveProductsUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    public VerifyActiveProductsService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean execute(List<UUID> productIds) {
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
