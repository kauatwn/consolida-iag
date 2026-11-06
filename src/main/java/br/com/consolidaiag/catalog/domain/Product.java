package br.com.consolidaiag.catalog.domain;

import br.com.consolidaiag.catalog.domain.exceptions.InvalidProductDataException;
import br.com.consolidaiag.catalog.domain.exceptions.InvalidProductStateException;

public final class Product {

    private final ProductId id;
    private String name;
    private UnitMeasure unitMeasure;
    private ProductStatus productStatus;

    private Product(ProductId id, String name, UnitMeasure unitMeasure, ProductStatus productStatus) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidProductDataException("Product name is required.");
        }

        if (unitMeasure == null) {
            throw new InvalidProductDataException("Unit of measure is required.");
        }

        if (productStatus == null) {
            throw new InvalidProductDataException("Product status is required.");
        }

        this.id = id;
        this.name = name.trim();
        this.unitMeasure = unitMeasure;
        this.productStatus = productStatus;
    }

    // Factory 1: Para a criação de um NOVO produto pelo sistema (nasce ACTIVE por padrão)
    public static Product create(String name, UnitMeasure unitMeasure) {
        return new Product(ProductId.generate(), name, unitMeasure, ProductStatus.ACTIVE);
    }

    // Factory 2: Para o JPA reconstituir o objeto vindo do banco de dados
    public static Product reconstitute(ProductId id, String name, UnitMeasure unitMeasure, ProductStatus productStatus) {
        return new Product(id, name, unitMeasure, productStatus);
    }

    public void close() {
        if (this.productStatus == ProductStatus.INACTIVE) {
            throw new InvalidProductStateException("The product is already inactive.");
        }

        this.productStatus = ProductStatus.INACTIVE;
    }

    public void open() {
        if (this.productStatus == ProductStatus.ACTIVE) {
            throw new InvalidProductStateException("The product is already active.");
        }

        this.productStatus = ProductStatus.ACTIVE;
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

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public boolean isActive() {
        return this.productStatus == ProductStatus.ACTIVE;
    }

    public boolean isInactive() {
        return this.productStatus == ProductStatus.INACTIVE;
    }
}