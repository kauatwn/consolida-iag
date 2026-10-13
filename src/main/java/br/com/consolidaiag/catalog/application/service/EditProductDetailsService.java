package br.com.consolidaiag.catalog.application.service;

import br.com.consolidaiag.catalog.application.dtos.request.EditProductDetailsPayload;
import br.com.consolidaiag.catalog.application.ports.in.EditProductDetailsUseCase;
import br.com.consolidaiag.catalog.application.ports.out.ProductRepositoryPort;
import br.com.consolidaiag.catalog.domain.Product;
import br.com.consolidaiag.catalog.domain.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditProductDetailsService implements EditProductDetailsUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    public EditProductDetailsService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    @Transactional
    public void execute(EditProductDetailsPayload payload) {

        Product product = productRepositoryPort.findById(payload.productId())
                .orElseThrow(() -> new ProductNotFoundException(payload.productId()));

        product.editDetails(payload.newName(), payload.newUnitMeasure());

        productRepositoryPort.save(product);
    }
}
