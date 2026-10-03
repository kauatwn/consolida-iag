package br.com.consolidaiag.weeklyrequest.adapters.out.persistence;

import jakarta.persistence.*;

@Entity
@Table(
    name = "request_items",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_request_product", columnNames = {"request_id", "product_id"})
    }
)
public class RequestItemJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false, precision = 10, scale = 2)
    private java.math.BigDecimal quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private WeeklyRequestJpaEntity request;

    // Construtor vazio exigido pelo JPA
    public RequestItemJpaEntity() {}

    public RequestItemJpaEntity(Long productId, java.math.BigDecimal quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public java.math.BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(java.math.BigDecimal quantity) {
        this.quantity = quantity;
    }

    public WeeklyRequestJpaEntity getRequest() {
        return request;
    }

    public void setRequest(WeeklyRequestJpaEntity request) {
        this.request = request;
    }
}