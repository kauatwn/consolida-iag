package br.com.consolidaiag.catalog.domain;

import br.com.consolidaiag.catalog.domain.exception.InvalidProductDataException;
import br.com.consolidaiag.catalog.domain.exception.InvalidProductStateException;

public final class Product {

    private final ProductId id;
    private String name;
    private UnitMeasure unitMeasure;
    private boolean active;

    private Product(ProductId id, String name, UnitMeasure unitMeasure, boolean active) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidProductDataException("O nome do produto é obrigatório.");
        }

        if (unitMeasure == null) {
            throw new InvalidProductDataException("A unidade de medida é obrigatória.");
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
            throw new InvalidProductStateException("O produto já está inativo.");
        }

        this.active = false;
    }

    public void activate() {
        if (this.active) {
            throw new InvalidProductStateException("O produto já está ativo.");
        }

        this.active = true;
    }

    public void updateDetails(String newName, UnitMeasure newMeasure) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new InvalidProductDataException("O nome do produto não pode ser vazio.");
        }

        if (newMeasure == null) {
            throw new InvalidProductDataException("A unidade de medida não pode ser nula.");
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