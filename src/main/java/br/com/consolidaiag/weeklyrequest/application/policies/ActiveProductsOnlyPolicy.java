package br.com.consolidaiag.weeklyrequest.application.policies;

import br.com.consolidaiag.weeklyrequest.application.ports.out.ProductCatalogPort;
import br.com.consolidaiag.weeklyrequest.domain.RequestItem;
import br.com.consolidaiag.weeklyrequest.domain.WeeklyRequest;
import br.com.consolidaiag.weeklyrequest.domain.exceptions.InactiveProductSelectionException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Valida a regra RN-02.1: Apenas produtos ativos do catálogo institucional podem compor uma solicitação.
 */
@Component
@Order(2)
public class ActiveProductsOnlyPolicy implements WeeklyRequestPolicy {

    private final ProductCatalogPort catalogPort;

    public ActiveProductsOnlyPolicy(ProductCatalogPort catalogPort) {
        this.catalogPort = catalogPort;
    }

    @Override
    public void validate(WeeklyRequest request) {
        if (request == null || request.getItems() == null || request.getItems().isEmpty()) {
            return;
        }

        List<Long> requestedProductIds = request.getItems().stream()
                .map(RequestItem::productId)
                .toList();

        if (!catalogPort.areAllProductsActive(requestedProductIds)) {
            throw new InactiveProductSelectionException(requestedProductIds);
        }
    }
}