package br.com.consolidaiag.catalog.application.service;

import br.com.consolidaiag.catalog.application.dtos.request.EditProductDetailsPayLoad;
import br.com.consolidaiag.catalog.application.ports.in.EditProductDetailsUseCase;
import br.com.consolidaiag.catalog.application.ports.out.ProductRepositoryPort;
import br.com.consolidaiag.catalog.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class EditProductDetailsService implements EditProductDetailsUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    public EditProductDetailsService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    public void execute(EditProductDetailsPayLoad payload) {
        // 1. Busca o produto utilizando a porta de saída
        Product product = productRepositoryPort.findById(payload.productId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com o ID fornecido: " + payload.productId()));

        // 2. Executa o comportamento de negócio contido na Entidade de Domínio
        product.updateDetails(payload.newName(), payload.newUnitMeasure());

        // 3. Persiste as alterações
        productRepositoryPort.save(product);
    }
}
