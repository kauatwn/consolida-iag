package br.com.consolidaiag.catalog.application.service;

import br.com.consolidaiag.catalog.application.dtos.request.DeactivateProductPayload;
import br.com.consolidaiag.catalog.application.ports.in.DeactivateProductUseCase;
import br.com.consolidaiag.catalog.application.ports.out.ProductRepositoryPort;
import br.com.consolidaiag.catalog.domain.Product;
import br.com.consolidaiag.catalog.domain.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeactivateProductService implements DeactivateProductUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    public DeactivateProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    @Transactional
    public void execute(DeactivateProductPayload payload) {

        Product product = productRepositoryPort.findById(payload.productId())
                .orElseThrow(() -> new ProductNotFoundException(payload.productId()));

        product.deactivate();

        productRepositoryPort.save(product);

    }
}
