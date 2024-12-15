/* (C)2024 */
package br.com.edu.order.domain;

import br.com.edu.order.domain.errors.InvalidPriceException;
import br.com.edu.order.domain.errors.InvalidQuantityException;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "Items")
public class Item {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "order_id", columnDefinition = "uuid")
    private UUID orderId;

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

    public void setPrice(final BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 1) {
            throw new InvalidPriceException(price);
        }
        this.price = price;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new InvalidQuantityException(quantity);
        }
        this.quantity = quantity;
    }

    public BigDecimal totalItem() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
