package br.com.edu.order.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "Items")
public class Item {

    @Id
    @GeneratedValue
    @Column( columnDefinition = "uuid", updatable = false )
    private UUID id;

    @Column(name = "product")
    private String product;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private int quantity;

    public Item() {
        this.id = UUID.randomUUID();
        this.price = BigDecimal.ZERO;
        this.quantity = 0;
    }

    public BigDecimal totalItem() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
