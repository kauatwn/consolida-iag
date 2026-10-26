package br.com.consolidaiag.catalog.application.service;

import br.com.consolidaiag.catalog.application.ports.in.VerifyActiveProductsUseCase;
import br.com.consolidaiag.catalog.application.ports.out.ProductRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class VerifyActiveProductsService implements VerifyActiveProductsUseCase {

    private static final Logger log = LoggerFactory.getLogger(VerifyActiveProductsService.class);

    private final ProductRepositoryPort productRepositoryPort;

    public VerifyActiveProductsService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean execute(List<UUID> productIds) {
        log.info("Iniciando verificação de produtos ativos. Total de IDs recebidos: {}", productIds != null ? productIds.size() : 0);

        if (productIds == null || productIds.isEmpty()) {
            log.debug("Lista de IDs vazia ou nula. Retornando true por padrão.");
            return true;
        }

        // Remove duplicados da lista (programação defensiva)
        List<UUID> uniqueIds = productIds.stream().distinct().toList();
        log.debug("Quantidade de IDs únicos após remoção de duplicados: {}", uniqueIds.size());

        // Consulta a base de dados real
        long activeCount = productRepositoryPort.countActiveProductsByIds(uniqueIds);

        boolean allActive = activeCount == uniqueIds.size();

        log.info("Verificação de produtos ativos concluída. Esperados: {}, Ativos encontrados: {}. Resultado: {}",
                uniqueIds.size(), activeCount, allActive);

        // Se a quantidade de produtos ativos encontrados for igual à quantidade de IDs únicos solicitados, está tudo certo.
        return allActive;
    }
}
