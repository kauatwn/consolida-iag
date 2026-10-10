package br.com.consolidaiag.weeklyrequest.adapters.out.persistence;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(
        name = "request_items",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_request_product", columnNames = {"request_id", "product_id"})
        }
)
public class RequestItemJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private WeeklyRequestJpaEntity request;

    // Construtor vazio exigido pelo JPA
    public RequestItemJpaEntity() {
    }

    public RequestItemJpaEntity(UUID productId, BigDecimal quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public WeeklyRequestJpaEntity getRequest() {
        return request;
    }

    public void setRequest(WeeklyRequestJpaEntity request) {
        this.request = request;
    }
}