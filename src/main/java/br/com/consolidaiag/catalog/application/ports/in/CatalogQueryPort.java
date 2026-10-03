package br.com.consolidaiag.catalog.application.ports.in;

import java.util.List;

public interface CatalogQueryPort {
    // Retorna true se TODOS os produtos da lista estiverem ativos
    boolean verifyProductsStatus(List<Long> productIds);
}