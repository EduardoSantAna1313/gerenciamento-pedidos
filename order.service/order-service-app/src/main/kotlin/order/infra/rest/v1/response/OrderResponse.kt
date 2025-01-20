package order.infra.rest.v1.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import order.domain.Order
import order.domain.Status
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class OrderResponse(
    val id: UUID? = null,

    val status: Status? = null,

    val created: LocalDateTime? = null,

    @JsonProperty("created_by")
    val createdBy: String? = null,

    val updated: LocalDateTime? = null,

    @JsonProperty("updated_by")
    val updatedBy: String? = null,

    val total: BigDecimal? = null,

    val items: List<ItemResponse>? = null
) {

    companion object {
        fun fromModel(order: Order): OrderResponse = OrderResponse(
            id = order.id,
            status = order.status,
            created = order.created,
            createdBy = order.createdBy,
            updated = order.updated,
            updatedBy = order.updatedBy,
            total = order.total,
            items = order.items.stream().map(ItemResponse::fromModel).toList()
        )
    }
}
