package br.com.consolidaiag.catalog.application.service;

import br.com.consolidaiag.catalog.application.dtos.response.CatalogProductResponse;
import br.com.consolidaiag.catalog.application.ports.in.ListCatalogProductsUseCase;
import br.com.consolidaiag.catalog.application.ports.out.ProductRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListCatalogProductsService implements ListCatalogProductsUseCase {

    private static final Logger log = LoggerFactory.getLogger(ListCatalogProductsService.class);

    private final ProductRepositoryPort productRepositoryPort;

    public ListCatalogProductsService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CatalogProductResponse> execute() {
        log.info("Iniciando listagem de produtos do catálogo.");

        List<CatalogProductResponse> products = productRepositoryPort.findAll().stream()
                .map(product -> new CatalogProductResponse(
                        product.getId().value(),
                        product.getName(),
                        product.getUnitMeasure().name(),
                        product.getCategory(),
                        product.getProductStatus()
                ))
                .toList();

        log.info("Listagem concluída com sucesso. Total de produtos encontrados: {}", products.size());

        return products;
    }
}
