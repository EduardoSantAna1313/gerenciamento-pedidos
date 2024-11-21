package br.com.edu.order.infra.rest.v1.response;

import br.com.edu.order.domain.Order;
import br.com.edu.order.domain.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private UUID id;

    private Status status;

    private LocalDateTime created;

    @JsonProperty("created_by")
    private String createdBy;

    private LocalDateTime updated;

    @JsonProperty("updated_by")
    private String updatedBy;

    private BigDecimal total;

    private List<ItemResponse> items;

    public static OrderResponse fromModel(final Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .status(order.getStatus())
                .created(order.getCreated())
                .createdBy(order.getCreatedBy())
                .updated(order.getUpdated())
                .updatedBy(order.getUpdatedBy())
                .total(order.getTotal())
                .items(order.getItems().stream().map(ItemResponse::fromModel).toList())
                .build();
    }

}
