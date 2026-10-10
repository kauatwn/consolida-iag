package br.com.consolidaiag.weeklyrequest.domain.exceptions;

import java.util.Collections;
import java.util.List;

/**
 * Lançada quando um pedido referencia produtos inativos ou inexistentes no catálogo (RN-02.1).
 */
public class InactiveProductSelectionException extends RuntimeException {

    private final List<Long> productIds;

    public InactiveProductSelectionException(List<Long> productIds) {
        super(String.format("One or more selected products are inactive or do not exist in the catalog: %s (RN-02.1).", productIds));
        this.productIds = productIds != null ? List.copyOf(productIds) : Collections.emptyList();
    }

    public InactiveProductSelectionException(String message) {
        super(message);
        this.productIds = Collections.emptyList();
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}
