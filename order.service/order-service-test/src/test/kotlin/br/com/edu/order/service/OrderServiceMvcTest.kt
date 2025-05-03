package br.com.edu.order.service

import br.com.edu.base.mockmvn.MockMvcBaseTest
import br.com.edu.order.domain.Order
import br.com.edu.order.repository.OrderRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired


internal class OrderServiceMvcTest: MockMvcBaseTest() {

    @Autowired
    private lateinit var service: OrderService

    @Autowired
    private lateinit var repository: OrderRepository

    @BeforeEach
    fun setUp() {
        repository.deleteAll()
    }

    @Test
    @DisplayName("Save batch")
    fun shouldSaveBatch() {
        for (i in 0..9) {
            val order = Order()
            order.createdBy = "teste"
            service.save(order)
        }

        assertEquals(10, repository.count())
    }

}
