package br.com.consolidaiag.catalog.application.service;

import br.com.consolidaiag.catalog.application.dtos.request.ActivateProductPayload;
import br.com.consolidaiag.catalog.application.ports.in.ActivateProductUseCase;
import br.com.consolidaiag.catalog.application.ports.out.ProductRepositoryPort;
import br.com.consolidaiag.catalog.domain.Product;
import br.com.consolidaiag.catalog.domain.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActivateProductService implements ActivateProductUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    public ActivateProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    @Transactional
    public void execute(ActivateProductPayload payload) {

        Product product = productRepositoryPort.findById(payload.productId())
                .orElseThrow(() -> new ProductNotFoundException(payload.productId()));

        product.activate();

        productRepositoryPort.save(product);
    }
}
