/* (C)2024 */
package br.com.edu.order.infra.rest.v1.response;

import br.com.edu.order.domain.Item;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemResponse {

    private UUID id;

    private String product;

    private BigDecimal price;

    private Integer quantity;


    public static ItemResponse fromModel(final Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .product(item.getProduct())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .build();
    }
}
