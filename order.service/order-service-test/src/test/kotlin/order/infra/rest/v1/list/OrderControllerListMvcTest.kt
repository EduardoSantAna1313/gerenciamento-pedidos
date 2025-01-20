package order.infra.rest.v1.list

import order.DatabaseConfiguration
import order.domain.Order
import order.repository.OrderRepository
import org.hamcrest.Matchers
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

@Import(DatabaseConfiguration::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class OrderControllerListMvcTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var sqsClient: SqsClient

    @Autowired
    lateinit var repository: OrderRepository

    @BeforeEach
    fun setUp() {
        repository.deleteAll()
    }

    @Test
    @DisplayName("Should list orders successfully with default params")
    fun shouldListOrdersSuccessfullyWithDefaultParams() {
        val orders = mutableListOf<Order>()
        for (i in 0..9) {
            val order = Order(
                createdBy = "test"
            )
            repository.save(order)
            orders.add(order)
        }

        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/orders")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath(".page.page").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath(".page.page_size").value(100))
            .andExpect(MockMvcResultMatchers.jsonPath(".page.total_pages").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath(".page.total_elements").value(10))
            .andExpect(MockMvcResultMatchers.jsonPath(".content.length()", Matchers.`is`(10)))
    }

    @Test
    @DisplayName("Should list orders successfully with pagination")
    fun shouldListOrdersSuccessfullyWithPagination() {
        for (i in 0..9) {
            val order = Order(
                createdBy = "test"
            )
            repository.save(order)
        }

        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/orders?page=0&size=2")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath(".page.page").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath(".page.page_size").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath(".page.total_pages").value(5))
            .andExpect(MockMvcResultMatchers.jsonPath(".page.total_elements").value(10))
            .andExpect(MockMvcResultMatchers.jsonPath(".content.length()", Matchers.`is`(2)))
    }

}
