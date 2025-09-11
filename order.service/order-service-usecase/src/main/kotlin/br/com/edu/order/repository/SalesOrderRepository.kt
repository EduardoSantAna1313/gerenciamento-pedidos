package br.com.edu.order.repository

import br.com.edu.order.domain.SalesOrder
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface SalesOrderRepository : JpaRepository<SalesOrder, UUID> {
    fun findByNumOrderAndActive(numOrder: Long, active: Boolean): List<SalesOrder>

    @Modifying
    @Query("UPDATE SalesOrder s SET s.active = false WHERE s.numOrder = :numOrder AND s.active = true")
    fun inactivateByNumOrder(numOrder: Long)

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from SalesOrder s where s.numOrder = :numOrder and s.active = true")
    fun findActiveByNumOrderWithLock(numOrder: Long): List<SalesOrder>
}