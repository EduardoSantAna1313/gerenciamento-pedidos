package br.com.edu.order.infra.rest.v1.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListResponse(

    @JsonProperty("content")
    val content: List<OrderResponse> = listOf(),

    @JsonProperty("page")
    val page: Page = Page()
)