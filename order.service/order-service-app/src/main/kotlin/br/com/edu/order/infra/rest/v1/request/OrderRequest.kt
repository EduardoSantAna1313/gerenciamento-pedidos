package br.com.edu.order.infra.rest.v1.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import br.com.edu.order.domain.Item

@JsonIgnoreProperties(ignoreUnknown = true)
data class OrderRequest(

    @JsonProperty("created_by")
    val createdBy: String? = null,

    @JsonProperty("origin")
    val origin: String? = "",

    @JsonProperty("items")
    val items: List<ItemRequest>? = listOf()
) {

    fun toModelItems(): MutableList<Item> = items?.map { obj: ItemRequest -> obj.toModelItem() }?.toMutableList() ?: mutableListOf()
}
