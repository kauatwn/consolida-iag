package br.com.consolidaiag.catalog.application.service;

import br.com.consolidaiag.catalog.application.dtos.request.RegisterProductPayload;
import br.com.consolidaiag.catalog.application.dtos.response.RegisterProductResponse;
import br.com.consolidaiag.catalog.application.ports.in.RegisterProductUseCase;
import br.com.consolidaiag.catalog.application.ports.out.ProductRepositoryPort;
import br.com.consolidaiag.catalog.domain.Product;
import br.com.consolidaiag.catalog.domain.exceptions.ProductAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterProductService implements RegisterProductUseCase {

    private static final Logger log = LoggerFactory.getLogger(RegisterProductService.class);

    private final ProductRepositoryPort productRepositoryPort;

    public RegisterProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    @Transactional
    public RegisterProductResponse execute(RegisterProductPayload payload) {
        log.info("Iniciando cadastro de novo produto com o nome: '{}'", payload.name());

        // Regra de negócio: Impede cadastro de produtos com o mesmo nome
        if (productRepositoryPort.existsByName(payload.name())) {
            log.warn("Tentativa de cadastro falhou. Já existe um produto cadastrado com o nome: '{}'.", payload.name());
            throw new ProductAlreadyExistsException("A product already exists with the name: '" + payload.name() + "'.");
        }

        // 1. Cria a entidade de domínio selada
        Product product = Product.create(payload.name(), payload.unitMeasure());

        // 2. Salva através da interface (quem vai implementar isso é o JPA depois)
        Product savedProduct = productRepositoryPort.save(product);

        log.info("Produto cadastrado com sucesso com o ID: {}", savedProduct.getId().value());

        // 3. Monta e devolve a resposta
        return new RegisterProductResponse(
                savedProduct.getId().value(),
                savedProduct.getName(),
                savedProduct.getUnitMeasure(),
                savedProduct.isActive()
        );
    }
}
