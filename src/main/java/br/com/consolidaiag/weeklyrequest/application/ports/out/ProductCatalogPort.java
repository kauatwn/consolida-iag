package br.com.consolidaiag.weeklyrequest.application.ports.out;

import java.util.List;
import java.util.UUID;

public interface ProductCatalogPort {
    boolean areAllProductsActive(List<UUID> productIds);
}