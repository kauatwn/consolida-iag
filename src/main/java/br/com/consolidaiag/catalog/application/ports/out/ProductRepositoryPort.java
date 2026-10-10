package br.com.consolidaiag.catalog.application.ports.out;

import br.com.consolidaiag.catalog.domain.Product;

import java.util.List;

public interface ProductRepositoryPort {
    Product save(Product product);

    // Importante para evitar produtos duplicados!
    boolean existsByName(String name);

    // Conta quantos produtos da lista estão ativos na base de dados
    long countActiveProductsByIds(List<Long> productIds);
}