package br.com.consolidaiag.catalog.application.service;

import br.com.consolidaiag.catalog.application.dtos.response.CatalogProductResponse;
import br.com.consolidaiag.catalog.application.ports.in.ListCatalogProductsUseCase;
import br.com.consolidaiag.catalog.application.ports.out.ProductRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListCatalogProductsService implements ListCatalogProductsUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    public ListCatalogProductsService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CatalogProductResponse> execute() {
        return productRepositoryPort.findAll().stream()
                .map(product -> new CatalogProductResponse(
                        product.getId().value(),
                        product.getName(),
                        product.getUnitMeasure().name(),
                        product.isActive()
                ))
                .toList();
    }
}
