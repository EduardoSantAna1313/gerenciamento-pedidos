package br.com.edu.order.repository

import br.com.edu.order.domain.Order
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface OrderRepository {

    fun findAll(pageable: Pageable): Page<Order>

    fun findById(id: UUID): Optional<Order>

    fun findByIdForUpdate(id: UUID): Optional<Order>

    fun save(order: Order): Order

    fun deleteAll()

    fun count(): Long
}
