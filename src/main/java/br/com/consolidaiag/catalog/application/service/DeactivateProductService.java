package br.com.consolidaiag.catalog.application.service;

import br.com.consolidaiag.catalog.application.dtos.request.DeactivateProductPayload;
import br.com.consolidaiag.catalog.application.ports.in.DeactivateProductUseCase;
import br.com.consolidaiag.catalog.application.ports.out.ProductRepositoryPort;
import br.com.consolidaiag.catalog.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class DeactivateProductService implements DeactivateProductUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    public DeactivateProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    public void execute(DeactivateProductPayload payload) {

        // 1. Busca o produto utilizando a porta de saída
        Product product = productRepositoryPort.findById(payload.productId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com o ID fornecido: " + payload.productId()));

        // 2. Executa o comportamento de negócio contido na Entidade de Domínio
        product.deactivate();

        // 3. Persiste as alterações
        productRepositoryPort.save(product);

    }
}
