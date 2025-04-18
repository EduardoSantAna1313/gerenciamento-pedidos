package br.com.edu.order.adapters.repository

import jakarta.persistence.LockModeType
import br.com.edu.order.domain.Order
import br.com.edu.order.repository.OrderRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PgOrderRepository : OrderRepository, JpaRepository<Order, UUID> {

    override fun findAll(pageable: Pageable): Page<Order>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from Order o where o.id = :id")
    override fun findByIdForUpdate(id: UUID): Optional<Order>

}
