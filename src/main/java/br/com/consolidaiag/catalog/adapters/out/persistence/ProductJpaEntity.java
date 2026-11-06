package br.com.consolidaiag.catalog.adapters.out.persistence;

import br.com.consolidaiag.catalog.domain.ProductStatus;
import br.com.consolidaiag.catalog.domain.UnitMeasure;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "products")
public class ProductJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit_measure", nullable = false)
    private UnitMeasure unitMeasure;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_status", nullable = false)
    private ProductStatus status;

    // Construtor vazio exigido pelo Hibernate
    public ProductJpaEntity() {
    }

    public ProductJpaEntity(UUID id, String name, UnitMeasure unitMeasure, ProductStatus status) {
        this.id = id;
        this.name = name;
        this.unitMeasure = unitMeasure;
        this.status = status;
    }

    // Getters e Setters anêmicos liberados (são para uso exclusivo do Framework/Mapper)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UnitMeasure getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(UnitMeasure unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public ProductStatus getProductStatus() {
        return status;
    }

    public void setProductStatus(ProductStatus status) {
        this.status = status;
    }
}