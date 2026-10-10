package br.com.consolidaiag.catalog.application.ports.in;

import java.util.List;
import java.util.UUID;

public interface CatalogQueryPort {
    // Retorna true se TODOS os produtos da lista estiverem ativos
    boolean verifyProductsStatus(List<UUID> productIds);
}