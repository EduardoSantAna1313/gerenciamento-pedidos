package br.com.edu.order.adapters.repository

import br.com.edu.order.repository.SalesOrderRepository
import org.springframework.stereotype.Repository

@Repository
interface PgSalesOrderRepository : SalesOrderRepository {
}