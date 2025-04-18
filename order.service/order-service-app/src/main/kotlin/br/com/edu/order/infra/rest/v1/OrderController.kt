package br.com.edu.order.infra.rest.v1

import br.com.edu.order.domain.Order
import br.com.edu.order.domain.errors.OrderNotFoundException
import br.com.edu.order.infra.rest.v1.request.OrderRequest
import br.com.edu.order.infra.rest.v1.response.ListResponse
import br.com.edu.order.infra.rest.v1.response.OrderResponse
import br.com.edu.order.infra.rest.v1.response.Page
import br.com.edu.order.usecases.CreateOrderUseCase
import br.com.edu.order.usecases.DeleteOrdersUseCase
import br.com.edu.order.usecases.DetailOrderUseCase
import br.com.edu.order.usecases.ListOrdersUseCase
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/v1/orders")
class OrderController(
    private val createOrderUseCase: CreateOrderUseCase,
    private val listOrdersUseCase: ListOrdersUseCase,
    private val detailOrderUseCase: DetailOrderUseCase,
    private val deleteOrdersUseCase: DeleteOrdersUseCase
) {

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody request: OrderRequest): ResponseEntity<*> {
        val input = Order()
        input.createdBy = request.createdBy
        input.items = request.toModelItems()

        val id = createOrderUseCase.execute(input)

        return ResponseEntity.created(URI.create("/v1/orders/$id")).build<Any>()
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun list(
        @RequestParam(value = "page", required = false, defaultValue = "0") page: Int?,
        @RequestParam(value = "size", required = false, defaultValue = "100") pageSize: Int?
    ): ResponseEntity<*> {

        val result = listOrdersUseCase.execute(
            page!!,
            pageSize!!
        )

        val listResponse = ListResponse(
            content = result.content.stream().map(OrderResponse::fromModel).toList(),
            page = Page(page = result.number,
                pageSize = result.size,
                totalPages = result.totalPages,
                totalElements = result.totalElements
            )
        )

        return ResponseEntity.ok<Any>(listResponse)
    }

    @GetMapping(value = ["/{order_id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun detail(@PathVariable("order_id", required = true) orderId: String?): ResponseEntity<*> {
        val result = detailOrderUseCase.execute(orderId).orElseThrow { OrderNotFoundException() }

        val response = OrderResponse.fromModel(result)

        return ResponseEntity.ok<Any>(response)
    }

    @DeleteMapping
    fun deleteAll(): ResponseEntity<*> {
        deleteOrdersUseCase.execute()

        return ResponseEntity.noContent().build<Any>()
    }
}
