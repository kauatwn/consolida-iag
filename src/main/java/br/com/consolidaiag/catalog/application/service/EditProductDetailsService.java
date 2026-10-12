package br.com.consolidaiag.catalog.application.service;

import br.com.consolidaiag.catalog.application.dtos.request.EditProductDetailsPayLoad;
import br.com.consolidaiag.catalog.application.ports.in.EditProductDetailsUseCase;
import br.com.consolidaiag.catalog.application.ports.out.ProductRepositoryPort;
import br.com.consolidaiag.catalog.domain.Product;
import br.com.consolidaiag.catalog.domain.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EditProductDetailsService implements EditProductDetailsUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    public EditProductDetailsService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    public void execute(EditProductDetailsPayLoad payload) {

        Product product = productRepositoryPort.findById(payload.productId())
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado com o ID fornecido: " + payload.productId()));

        product.updateDetails(payload.newName(), payload.newUnitMeasure());

        productRepositoryPort.save(product);
    }
}
