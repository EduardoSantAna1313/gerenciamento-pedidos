package order.service

import order.domain.Order
import order.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.function.Consumer

@Service
class OrderService (
    val repository: OrderRepository
) {

    private val logger = LoggerFactory.getLogger(OrderService::class.java)

    @Transactional
    fun save(order: Order) {
        val saved = repository.save(order)
        logger.info("Saved order {}", saved.id)
    }

    @Transactional
    fun saveAll(orders: List<Order>) {
        logger.info("Inserting {} orders", orders.size)
        orders.forEach(Consumer { obj: Order -> obj.calculateTotal() })
        orders.forEach(Consumer { obj: Order -> obj.changeProcessed() })

        val saved = mutableListOf<Order>()

        orders.forEach {
            saved.add(repository.save(it))
        }

        logger.info("Saved {} orders", saved)
    }

}
