/* (C)2024 */
package br.com.edu.order.infra.rest.v1.create;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.edu.order.DatabaseConfiguration;
import br.com.edu.order.domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Import(DatabaseConfiguration.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
class OrderControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SqsClient sqsClient;

    @Autowired
    private OrderRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void shouldCreateMessageSuccessfully() throws Exception {

        var mockResponse = Mockito.mock(ReceiveMessageResponse.class);

        when(mockResponse.hasMessages()).thenReturn(false);

        when(sqsClient.receiveMessage(any(ReceiveMessageRequest.class))).thenReturn(mockResponse);

        when(sqsClient.sendMessage(any(SendMessageRequest.class))).thenReturn(null);

        final var body = """
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
                """;

        mockMvc.perform(post("/v1/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
        ).andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

        verify(sqsClient, times(1)).sendMessage(any(SendMessageRequest.class));
    }

}
