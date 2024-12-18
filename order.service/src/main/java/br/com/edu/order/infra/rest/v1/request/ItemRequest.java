package br.com.edu.order.infra.rest.v1.request;

import br.com.edu.order.domain.Item;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemRequest {

    private String product;

    private BigDecimal price;

    private Integer quantity;

    public ItemRequest() {
        product = "";
        price = BigDecimal.ZERO;
        quantity = 0;
    }

    public Item toModelItem() {
        var item = new Item();
        item.setProduct(this.product);
        item.setPrice(this.price);
        item.setQuantity(this.quantity);
        return item;
    }
}
