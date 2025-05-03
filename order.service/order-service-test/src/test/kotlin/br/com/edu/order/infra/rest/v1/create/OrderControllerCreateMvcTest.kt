package br.com.edu.order.infra.rest.v1.create

import br.com.edu.base.mockmvn.MockMvcBaseTest
import br.com.edu.order.repository.OrderRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse
import software.amazon.awssdk.services.sqs.model.SendMessageRequest

internal class OrderControllerCreateMvcTest : MockMvcBaseTest(){


    @MockBean
    private lateinit var sqsClient: SqsClient

    @Autowired
    private lateinit var repository: OrderRepository

    @BeforeEach
    fun setUp() {
        repository.deleteAll()
    }

    @Test
    fun shouldCreateMessageSuccessfully() {
        val mockResponse = Mockito.mock(ReceiveMessageResponse::class.java)

        `when`(mockResponse.hasMessages()).thenReturn(false)

        `when`(
            sqsClient.receiveMessage(
                any(ReceiveMessageRequest::class.java)
            )
        ).thenReturn(mockResponse)

        `when`(
            sqsClient.sendMessage(
                any(SendMessageRequest::class.java)
            )
        ).thenReturn(null)

        val body = """
                {
                	"created_by": "Eduardo",
                	"origin": "External-A",
                	"items": [
                		{
                			"prodcut": "umidificador de ar",
                			"price": 5213.57,
                			"quantity": 5
                		}
                	]
                }
                
                """.trimIndent()

        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
        ).andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.header().exists("Location"))

        verify(sqsClient, Mockito.times(1)).sendMessage(
            any(SendMessageRequest::class.java)
        )
    }

}
