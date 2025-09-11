package br.com.edu.order.usecases

import br.com.edu.order.domain.SalesOrder
import br.com.edu.order.service.SalesOrderService
import org.springframework.stereotype.Service

@Service
class CreateSalesOrderUseCase(
    private val salesOrderService: SalesOrderService
) {
    fun execute(salesOrders: List<SalesOrder>): List<SalesOrder> {
        return salesOrderService.create(salesOrders)
    }
}