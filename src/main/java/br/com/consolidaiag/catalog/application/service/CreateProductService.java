package br.com.consolidaiag.catalog.application.service;

import br.com.consolidaiag.catalog.application.dtos.request.CreateProductPayload;
import br.com.consolidaiag.catalog.application.dtos.response.CreateProductResponse;
import br.com.consolidaiag.catalog.application.ports.in.CreateProductUseCase;
import br.com.consolidaiag.catalog.application.ports.out.ProductRepositoryPort;
import br.com.consolidaiag.catalog.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateProductService implements CreateProductUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    public CreateProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    @Transactional
    public CreateProductResponse execute(CreateProductPayload payload) {
        // Regra de negócio: Impede cadastro de produtos com o mesmo nome
        if (productRepositoryPort.existsByName(payload.name())) {
            throw new IllegalArgumentException("Já existe um produto cadastrado com o nome: " + payload.name());
        }

        // 1. Cria a entidade de domínio selada
        Product product = Product.create(payload.name(), payload.unitMeasure());

        // 2. Salva através da interface (quem vai implementar isso é o JPA depois)
        Product savedProduct = productRepositoryPort.save(product);

        // 3. Monta e devolve a resposta
        return new CreateProductResponse(
                savedProduct.getId().value(),
                savedProduct.getName(),
                savedProduct.getUnitMeasure(),
                savedProduct.isActive()
        );
    }
}