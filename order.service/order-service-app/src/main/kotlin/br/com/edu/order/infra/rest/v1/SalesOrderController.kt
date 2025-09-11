package br.com.edu.order.infra.rest.v1

import br.com.edu.order.infra.rest.v1.request.SalesOrderRequest
import br.com.edu.order.infra.rest.v1.response.SalesOrderResponse
import br.com.edu.order.service.SalesOrderService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/v1/sales-orders")
class SalesOrderController(
    private val salesOrderService: SalesOrderService
) {

    @PostMapping(
        "/{num_order}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun create(
        @PathVariable("num_order") numOrder: Long,
        @RequestBody request: List<SalesOrderRequest>
    ): ResponseEntity<*> {
        val salesOrders = request.map { it.toSalesOrder(numOrder) }
        val createdOrders = salesOrderService.create(salesOrders)
        val response = createdOrders.map { SalesOrderResponse.fromSalesOrder(it) }
        return ResponseEntity.created(URI.create("/v1/sales-orders/$numOrder")).body(response)
    }
}