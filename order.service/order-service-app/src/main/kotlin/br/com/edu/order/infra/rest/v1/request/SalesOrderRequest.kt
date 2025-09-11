package br.com.edu.order.infra.rest.v1.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import br.com.edu.order.domain.SalesOrder
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
data class SalesOrderRequest(

    @JsonProperty("num_item")
    val numItem: Int,

    @JsonProperty("product_id")
    val productId: String,

    @JsonProperty("price")
    val price: BigDecimal,

    @JsonProperty("quantity")
    val quantity: Int,

    @JsonProperty("val_base_calculo")
    val valBaseCalculo: BigDecimal? = null,

    @JsonProperty("val_icsm")
    val valIcsm: BigDecimal? = null
) {
    fun toSalesOrder(numOrder: Long): SalesOrder {
        return SalesOrder(
            numOrder = numOrder,
            numItem = this.numItem,
            productId = this.productId,
            price = this.price,
            quantity = this.quantity,
            valBaseCalculo = this.valBaseCalculo,
            valIcsm = this.valIcsm
        )
    }
}