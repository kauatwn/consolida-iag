package br.com.consolidaiag.weeklyrequest.adapters.out.integration;

import br.com.consolidaiag.weeklyrequest.application.ports.out.ProductCatalogPort;
import br.com.consolidaiag.catalog.application.ports.in.CatalogQueryPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CatalogIntegrationAdapter implements ProductCatalogPort {

    // O tradutor agora tem uma injeção de dependência do serviço vizinho
    private final CatalogQueryPort catalogQueryPort;

    public CatalogIntegrationAdapter(CatalogQueryPort catalogQueryPort) {
        this.catalogQueryPort = catalogQueryPort;
    }

    @Override
    public boolean areAllProductsActive(List<Long> productIds) {
        // O seu Adapter repassa a pergunta para o serviço real do Catálogo.
        // É o vizinho quem vai fazer o 'SELECT' na tabela de produtos dele.
        return catalogQueryPort.verifyProductsStatus(productIds);
    }
}