package br.com.edu.order.service

import br.com.edu.order.domain.SalesOrder
import br.com.edu.order.repository.SalesOrderRepository
import br.com.edu.order.usecases.calc_engine.PriceTablePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SalesOrderService(
    private val salesOrderRepository: SalesOrderRepository,
    private val priceTablePort: PriceTablePort
) {

    @Transactional
    fun create(salesOrders: List<SalesOrder>): List<SalesOrder> {
        if (salesOrders.isEmpty()) {
            return emptyList()
        }

        val numOrder = salesOrders.first().numOrder
        salesOrderRepository.inactivateByNumOrder(numOrder)

        salesOrders.forEach {
            it.active = true
            it.productId = "123"
            it.jsonTabelaCheia = null
            it.jsonTabelaPraticada = null
        }

        val savedOrders = salesOrderRepository.saveAll(salesOrders)

        val priceTableResult = priceTablePort.getPriceTable(numOrder)

        val updatedOrders = savedOrders.map {
            it.jsonTabelaCheia = priceTableResult.jsonTabelaCheia
            it.jsonTabelaPraticada = priceTableResult.jsonTabelaPraticada
            it
        }

        return salesOrderRepository.saveAll(updatedOrders)
    }
}