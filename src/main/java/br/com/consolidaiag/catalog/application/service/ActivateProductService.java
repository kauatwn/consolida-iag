package br.com.consolidaiag.catalog.application.service;

import br.com.consolidaiag.catalog.application.dtos.request.ActivateProductPayload;
import br.com.consolidaiag.catalog.application.ports.in.ActivateProductUseCase;
import br.com.consolidaiag.catalog.application.ports.out.ProductRepositoryPort;
import br.com.consolidaiag.catalog.domain.Product;
import br.com.consolidaiag.catalog.domain.exceptions.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActivateProductService implements ActivateProductUseCase {

    private static final Logger log = LoggerFactory.getLogger(ActivateProductService.class);

    private final ProductRepositoryPort productRepositoryPort;

    public ActivateProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    @Transactional
    public void execute(ActivateProductPayload payload) {
        log.info("Iniciando ativação do produto com ID: {}", payload.productId());

        Product product = productRepositoryPort.findById(payload.productId())
                .orElseGet(() -> {
                    log.warn("Produto com ID {} não encontrado para ativação.", payload.productId());
                    throw new ProductNotFoundException(payload.productId());
                });

        product.activate();

        productRepositoryPort.save(product);

        log.info("Produto com ID {} ativado com sucesso.", payload.productId());
    }
}
