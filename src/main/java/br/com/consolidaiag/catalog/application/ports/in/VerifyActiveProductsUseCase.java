package br.com.consolidaiag.catalog.application.ports.in;

import java.util.List;
import java.util.UUID;

public interface VerifyActiveProductsUseCase {
    boolean execute(List<UUID> productIds);
}