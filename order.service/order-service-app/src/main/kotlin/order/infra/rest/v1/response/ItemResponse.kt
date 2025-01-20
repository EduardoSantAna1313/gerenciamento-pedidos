package order.infra.rest.v1.response

import com.fasterxml.jackson.annotation.JsonInclude
import order.domain.Item
import java.math.BigDecimal
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ItemResponse(
    val id: UUID? = null,

    val product: String? = null,

    val price: BigDecimal? = null,

    val quantity: Int? = null
) {

    companion object {
        fun fromModel(item: Item) = ItemResponse(
            id = item.id,
            product = item.product,
            price = item.price,
            quantity = item.quantity
        )
    }
}
