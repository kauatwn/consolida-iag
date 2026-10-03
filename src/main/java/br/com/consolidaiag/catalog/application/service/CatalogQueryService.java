package br.com.consolidaiag.catalog.application.service;

import br.com.consolidaiag.catalog.application.ports.in.CatalogQueryPort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CatalogQueryService implements CatalogQueryPort {

    @Override
    public boolean verifyProductsStatus(List<Long> productIds) {
        /*
         * TODO: IMPLEMENTAÇÃO MOCKADA TEMPORÁRIA.
         * Finge que qualquer produto que a Líder de Cozinha pedir existe e está ativo.
         * Quando o subdomínio de catálogo for desenvolvido de verdade, 
         * este método chamará a porta de saída (ProductRepositoryPort) 
         * para bater no banco de dados.
         */
        return true; 
    }
}