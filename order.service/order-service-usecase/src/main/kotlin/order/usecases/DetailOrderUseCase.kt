package order.usecases

import order.domain.Order
import order.domain.errors.OrderNotFoundException
import order.repository.OrderRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class DetailOrderUseCase(private val repository: OrderRepository) {

    fun execute(orderId: String?): Optional<Order> {

        if (orderId == null) throw OrderNotFoundException()

        return repository.findById(UUID.fromString(orderId))
    }

}
