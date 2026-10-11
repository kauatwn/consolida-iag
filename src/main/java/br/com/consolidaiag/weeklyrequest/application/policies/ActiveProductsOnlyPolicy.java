package br.com.consolidaiag.weeklyrequest.application.policies;

import br.com.consolidaiag.weeklyrequest.application.ports.out.CheckActiveProductsPort;
import br.com.consolidaiag.weeklyrequest.domain.RequestItem;
import br.com.consolidaiag.weeklyrequest.domain.WeeklyRequest;
import br.com.consolidaiag.weeklyrequest.domain.exceptions.InactiveProductSelectionException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Valida a regra RN-02.1: Apenas produtos ativos do catálogo institucional podem compor uma solicitação.
 */
@Component
@Order(2)
public class ActiveProductsOnlyPolicy implements WeeklyRequestPolicy {

    private final CheckActiveProductsPort checkActiveProductsPort;

    public ActiveProductsOnlyPolicy(CheckActiveProductsPort checkActiveProductsPort) {
        this.checkActiveProductsPort = checkActiveProductsPort;
    }

    @Override
    public void validate(WeeklyRequest request) {
        if (request == null || request.getItems().isEmpty()) {
            return;
        }

        List<UUID> requestedProductIds = request.getItems().stream()
                .map(RequestItem::productId)
                .toList();

        if (!checkActiveProductsPort.areAllProductsActive(requestedProductIds)) {
            throw new InactiveProductSelectionException(requestedProductIds);
        }
    }
}