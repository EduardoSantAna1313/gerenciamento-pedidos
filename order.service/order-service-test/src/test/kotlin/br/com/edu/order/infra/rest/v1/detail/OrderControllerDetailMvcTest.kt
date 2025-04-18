package br.com.edu.order.infra.rest.v1.detail

import br.com.edu.base.db.DatabaseConfiguration
import br.com.edu.order.domain.Item
import br.com.edu.order.domain.Order
import br.com.edu.order.repository.OrderRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import software.amazon.awssdk.services.sqs.SqsClient
import java.math.BigDecimal
import java.util.*

@Import(DatabaseConfiguration::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class OrderControllerDetailMvcTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var sqsClient: SqsClient

    @Autowired
    private lateinit var repository: OrderRepository

    @BeforeEach
    fun setUp() {
        repository.deleteAll()
    }

    @Test
    @DisplayName("Should detail an order successfully")
    fun shouldDetailAnOrderSuccessfully() {
        val item = Item()
        item.quantity = 2
        item.product = "test product"
        item.price = BigDecimal.valueOf(0.01)

        val order = Order()
        order.createdBy = "teste"
        order.addItem(item)

        repository.save(order)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/orders/" + order.id)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath(".total").value(order.total.toDouble()))
            .andExpect(MockMvcResultMatchers.jsonPath(".status").value(order.status.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath(".created_by").value(order.createdBy))
    }

    @Test
    @DisplayName("Should return not found")
    fun shouldReturnNotFound() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/orders/" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound())
    }

}
