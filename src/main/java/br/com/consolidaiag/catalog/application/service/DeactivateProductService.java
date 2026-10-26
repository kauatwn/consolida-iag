package br.com.consolidaiag.catalog.application.service;

import br.com.consolidaiag.catalog.application.dtos.request.DeactivateProductPayload;
import br.com.consolidaiag.catalog.application.ports.in.DeactivateProductUseCase;
import br.com.consolidaiag.catalog.application.ports.out.ProductRepositoryPort;
import br.com.consolidaiag.catalog.domain.Product;
import br.com.consolidaiag.catalog.domain.exceptions.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeactivateProductService implements DeactivateProductUseCase {

    private static final Logger log = LoggerFactory.getLogger(DeactivateProductService.class);

    private final ProductRepositoryPort productRepositoryPort;

    public DeactivateProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    @Transactional
    public void execute(DeactivateProductPayload payload) {
        log.info("Iniciando desativação do produto com ID: {}", payload.productId());

        Product product = productRepositoryPort.findById(payload.productId())
                .orElseGet(() -> {
                    log.warn("Produto com ID {} não encontrado para desativação.", payload.productId());
                    throw new ProductNotFoundException(payload.productId());
                });

        product.deactivate();

        productRepositoryPort.save(product);

        log.info("Produto com ID {} desativado com sucesso.", payload.productId());
    }
}
