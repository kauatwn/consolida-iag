package br.com.consolidaiag.weeklyrequest.domain.exceptions;

import br.com.consolidaiag.shared.DomainException;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Lançada quando um pedido referencia produtos inativos ou inexistentes no catálogo (RN-02.1).
 */
public final class InactiveProductSelectionException extends DomainException {

    private final List<UUID> productIds;

    public InactiveProductSelectionException(List<UUID> productIds) {
        super(String.format("One or more selected products are inactive or do not exist in the catalog: %s (RN-02.1).", productIds));
        this.productIds = productIds != null ? List.copyOf(productIds) : Collections.emptyList();
    }

    public InactiveProductSelectionException(String message) {
        super(message);
        this.productIds = Collections.emptyList();
    }

    public List<UUID> getProductIds() {
        return productIds;
    }
}