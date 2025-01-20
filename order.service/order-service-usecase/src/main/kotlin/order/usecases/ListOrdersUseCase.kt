package order.usecases

import order.domain.Order
import order.repository.OrderRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class ListOrdersUseCase(private val repository: OrderRepository) {

    fun execute(page: Int, pageSize: Int): Page<Order> {
        return repository.findAll(PageRequest.of(page, pageSize))
    }

}
