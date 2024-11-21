package br.com.edu.order.infra.rest.v1.request;

import br.com.edu.order.domain.Item;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderRequest {

    @JsonProperty("created_by")
    private String createdBy;

    private String origin;

    private List<ItemRequest> items;

    public OrderRequest() {
        items = new ArrayList<>();
    }

    public List<ItemRequest> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    public List<Item> toModelItems() {
        return getItems().stream().map(ItemRequest::toModelItem).toList();
    }
}
