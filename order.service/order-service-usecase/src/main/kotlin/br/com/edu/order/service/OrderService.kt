package br.com.edu.order.service

import jakarta.persistence.LockModeType
import br.com.edu.order.domain.Item
import br.com.edu.order.domain.Order
import br.com.edu.order.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.Optional
import java.util.UUID

@Service
class OrderService (
    val repository: OrderRepository
) {

    private val logger = LoggerFactory.getLogger(OrderService::class.java)

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Transactional
    fun get(id: UUID): Optional<Order> {
        return repository.findById(id)
    }

    @Transactional
    fun save(order: Order): Order {
        return repository.save(order)
    }

    @Transactional
    fun update(id: UUID, items: List<Item>) {

        try {

            val order = repository.findByIdForUpdate(id)
            order.ifPresent {
                val initItems = it.items.size
                it.updated = LocalDateTime.now()
                it.items = items.toMutableList()

                it.calculateTotal()

                it.items.size

                repository.save(it)

                logger.info("Atualizado order $id start: $initItems, now: ${it.items.size}")

            }
        }catch (error: Exception) {
            logger.error("Ocorreu um erro ao fazer update {}", error.message)
        }
    }
}
