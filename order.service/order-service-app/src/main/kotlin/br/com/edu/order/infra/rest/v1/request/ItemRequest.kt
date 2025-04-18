package br.com.edu.order.infra.rest.v1.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import br.com.edu.order.domain.Item
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
data class ItemRequest(

    @JsonProperty("product")
    val product: String = "",

    @JsonProperty("price")
    val price: BigDecimal = BigDecimal.ZERO,

    @JsonProperty("quantity")
    val quantity: Int = 0

) {

    fun toModelItem(): Item {
        val item = Item()
        item.product = this.product
        item.price = this.price
        item.quantity = this.quantity
        return item
    }

}
