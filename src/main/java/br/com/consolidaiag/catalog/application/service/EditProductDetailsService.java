package br.com.consolidaiag.catalog.application.service;

import br.com.consolidaiag.catalog.application.dtos.request.EditProductDetailsPayload;
import br.com.consolidaiag.catalog.application.ports.in.EditProductDetailsUseCase;
import br.com.consolidaiag.catalog.application.ports.out.ProductRepositoryPort;
import br.com.consolidaiag.catalog.domain.Product;
import br.com.consolidaiag.catalog.domain.exceptions.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditProductDetailsService implements EditProductDetailsUseCase {

    private static final Logger log = LoggerFactory.getLogger(EditProductDetailsService.class);

    private final ProductRepositoryPort productRepositoryPort;

    public EditProductDetailsService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    @Transactional
    public void execute(EditProductDetailsPayload payload) {
        log.info("Iniciando edição de detalhes do produto com ID: {}", payload.productId());

        Product product = productRepositoryPort.findById(payload.productId())
                .orElseGet(() -> {
                    log.warn("Produto com ID {} não encontrado para edição.", payload.productId());
                    throw new ProductNotFoundException(payload.productId());
                });

        product.editDetails(payload.newName(), payload.newUnitMeasure());

        productRepositoryPort.save(product);

        log.info("Detalhes do produto com ID {} atualizados com sucesso.", payload.productId());
    }
}
