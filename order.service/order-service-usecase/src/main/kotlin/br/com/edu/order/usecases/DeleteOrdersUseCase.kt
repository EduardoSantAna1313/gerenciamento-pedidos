package br.com.edu.order.usecases

import br.com.edu.order.repository.OrderRepository
import org.springframework.stereotype.Service


@Service
class DeleteOrdersUseCase(private val repository: OrderRepository) {
    fun execute() {
        repository.deleteAll()
    }
}