package br.com.edu.order.service

import br.com.edu.order.domain.SalesOrder
import br.com.edu.order.repository.SalesOrderRepository
import br.com.edu.order.usecases.calc_engine.PriceTablePort
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class SalesOrderService(
    private val salesOrderRepository: SalesOrderRepository,
    private val priceTablePort: PriceTablePort,
    private val entityManager: EntityManager
) {

    @Transactional
    fun create(salesOrders: List<SalesOrder>): List<SalesOrder> {
        if (salesOrders.isEmpty()) {
            return emptyList()
        }

        val numOrder = salesOrders.first().numOrder
        inactivateExistingOrders(numOrder) // This method will now acquire the lock

        salesOrders.forEach {
            it.active = true
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun inactivateExistingOrders(numOrder: Long) {
        // Acquire a pessimistic write lock on active orders for this num_order
        salesOrderRepository.findActiveByNumOrderWithLock(numOrder)
        // Now perform the inactivation
        salesOrderRepository.inactivateByNumOrder(numOrder)
        entityManager.flush()
    }
}