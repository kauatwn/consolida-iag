package br.com.consolidaiag.weeklyrequest.adapters.out.integration;

import br.com.consolidaiag.weeklyrequest.application.ports.out.CheckActiveProductsPort;
import br.com.consolidaiag.catalog.application.ports.in.VerifyActiveProductsUseCase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CatalogIntegrationAdapter implements CheckActiveProductsPort {

    // O tradutor agora tem uma injeção de dependência do serviço vizinho
    private final VerifyActiveProductsUseCase verifyActiveProductsUseCase;

    public CatalogIntegrationAdapter(VerifyActiveProductsUseCase verifyActiveProductsUseCase) {
        this.verifyActiveProductsUseCase = verifyActiveProductsUseCase;
    }

    @Override
    public boolean areAllProductsActive(List<UUID> productIds) {
        // O seu Adapter repassa a pergunta para o serviço real do Catálogo.
        // É o vizinho quem vai fazer o 'SELECT' na tabela de produtos dele.
        return verifyActiveProductsUseCase.execute(productIds);
    }
}
