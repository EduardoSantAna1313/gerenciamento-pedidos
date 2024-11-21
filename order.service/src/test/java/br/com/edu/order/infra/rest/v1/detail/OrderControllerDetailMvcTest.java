package br.com.edu.order.infra.rest.v1.detail;

import br.com.edu.order.DatabaseConfiguration;
import br.com.edu.order.domain.Item;
import br.com.edu.order.domain.Order;
import br.com.edu.order.domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(DatabaseConfiguration.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
class OrderControllerDetailMvcTest {

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
    @DisplayName("Should detail an order successfully")
    void shoudDetailAnOrderSuccessfully() throws Exception {

        final var item = new Item();
        item.setQuantity(2);
        item.setProduct("test product");
        item.setPrice(BigDecimal.valueOf(0.01));

        final var order = new Order();
        order.setCreatedBy("teste");
        order.addItem(item);

        repository.save(order);

        mockMvc.perform(get("/v1/orders/" + order.getId())
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath(".total").value(order.getTotal().doubleValue()))
                .andExpect(jsonPath(".status").value(order.getStatus().toString()))
                .andExpect(jsonPath(".created_by").value(order.getCreatedBy()));
    }

    @Test
    @DisplayName("Should return not found")
    void shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/v1/orders/" + UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound());
    }

}
