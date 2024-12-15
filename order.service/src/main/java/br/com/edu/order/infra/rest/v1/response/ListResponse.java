/* (C)2024 */
package br.com.edu.order.infra.rest.v1.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ListResponse {

    @JsonProperty("content")
    private List<OrderResponse> content;

    @JsonProperty("page")
    private Page page;
}
