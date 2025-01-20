package order.usecases

import order.repository.OrderRepository
import org.springframework.stereotype.Service


@Service
class DeleteOrdersUseCase(private val repository: OrderRepository) {
    fun execute() {
        repository.deleteAll()
    }
}