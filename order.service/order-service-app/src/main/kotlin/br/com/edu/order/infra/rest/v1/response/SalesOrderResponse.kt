package br.com.edu.order.infra.rest.v1.response

import br.com.edu.order.domain.SalesOrder
import br.com.edu.order.domain.Status
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SalesOrderResponse(
    val id: UUID,
    @JsonProperty("num_order")
    val numOrder: Long,
    @JsonProperty("num_item")
    val numItem: Int,
    val status: Status,
    val created: LocalDateTime,
    @JsonProperty("created_by")
    val createdBy: String?,
    val updated: LocalDateTime,
    @JsonProperty("updated_by")
    val updatedBy: String?,
    @JsonProperty("val_total")
    val valTotal: BigDecimal,
    @JsonProperty("product_id")
    val productId: String,
    val price: BigDecimal,
    val quantity: Int,
    @JsonProperty("val_base_calculo")
    val valBaseCalculo: BigDecimal?,
    @JsonProperty("val_icsm")
    val valIcsm: BigDecimal?,
    @JsonProperty("json_tabela_cheia")
    val jsonTabelaCheia: String?,
    @JsonProperty("json_tabela_praticada")
    val jsonTabelaPraticada: String?,
    val active: Boolean
) {
    companion object {
        fun fromSalesOrder(salesOrder: SalesOrder): SalesOrderResponse {
            return SalesOrderResponse(
                id = salesOrder.id,
                numOrder = salesOrder.numOrder,
                numItem = salesOrder.numItem,
                status = salesOrder.status,
                created = salesOrder.created,
                createdBy = salesOrder.createdBy,
                updated = salesOrder.updated,
                updatedBy = salesOrder.updatedBy,
                valTotal = salesOrder.valTotal,
                productId = salesOrder.productId,
                price = salesOrder.price,
                quantity = salesOrder.quantity,
                valBaseCalculo = salesOrder.valBaseCalculo,
                valIcsm = salesOrder.valIcsm,
                jsonTabelaCheia = salesOrder.jsonTabelaCheia,
                jsonTabelaPraticada = salesOrder.jsonTabelaPraticada,
                active = salesOrder.active
            )
        }
    }
}