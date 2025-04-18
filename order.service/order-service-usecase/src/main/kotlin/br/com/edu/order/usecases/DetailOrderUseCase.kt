package br.com.edu.order.usecases

import br.com.edu.order.domain.Order
import br.com.edu.order.domain.errors.OrderNotFoundException
import br.com.edu.order.repository.OrderRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class DetailOrderUseCase(private val repository: OrderRepository) {

    fun execute(orderId: String?): Optional<Order> {

        if (orderId == null) throw OrderNotFoundException()

        return repository.findById(UUID.fromString(orderId))
    }

}
