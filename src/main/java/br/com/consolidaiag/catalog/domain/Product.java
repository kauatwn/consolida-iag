package br.com.consolidaiag.catalog.domain;

import br.com.consolidaiag.catalog.domain.exceptions.InvalidProductDataException;
import br.com.consolidaiag.catalog.domain.exceptions.InvalidProductStateException;

public final class Product {

    private final ProductId id;
    private String name;
    private UnitMeasure unitMeasure;
    private boolean active;

    private Product(ProductId id, String name, UnitMeasure unitMeasure, boolean active) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidProductDataException("Product name is required.");
        }

        if (unitMeasure == null) {
            throw new InvalidProductDataException("Unit of measure is required.");
        }

        this.id = id;
        this.name = name.trim();
        this.unitMeasure = unitMeasure;
        this.active = active;
    }

    // Factory 1: Para a criação de um NOVO produto pelo sistema (nasce ativo por padrão)
    public static Product create(String name, UnitMeasure unitMeasure) {
        return new Product(ProductId.generate(), name, unitMeasure, true);
    }

    // Factory 2: Para o JPA reconstituir o objeto vindo do banco de dados
    public static Product reconstitute(ProductId id, String name, UnitMeasure unitMeasure, boolean active) {
        return new Product(id, name, unitMeasure, active);
    }

    public void deactivate() {
        if (!this.active) {
            throw new InvalidProductStateException("The product is already inactive.");
        }

        this.active = false;
    }

    public void activate() {
        if (this.active) {
            throw new InvalidProductStateException("The product is already active.");
        }

        this.active = true;
    }

    public void editDetails(String newName, UnitMeasure newMeasure) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new InvalidProductDataException("Product name cannot be empty.");
        }

        if (newMeasure == null) {
            throw new InvalidProductDataException("Unit of measure cannot be null.");
        }

        this.name = newName.trim();
        this.unitMeasure = newMeasure;
    }

    public ProductId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UnitMeasure getUnitMeasure() {
        return unitMeasure;
    }

    public boolean isActive() {
        return active;
    }
}