package br.com.consolidaiag.weeklyrequest.application.ports.out;

import java.util.List;

public interface ProductCatalogPort {
    boolean areAllProductsActive(List<Long> productIds);
}
