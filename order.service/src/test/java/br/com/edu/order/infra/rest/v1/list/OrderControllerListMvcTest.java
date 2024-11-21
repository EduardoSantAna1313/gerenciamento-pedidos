package br.com.edu.order.infra.rest.v1.list;

import br.com.edu.order.DatabaseConfiguration;
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
import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(DatabaseConfiguration.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
class OrderControllerListMvcTest {

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
    @DisplayName("Should list orders successfully with default params")
    void shouldListOrdersSuccessfullyWithDefaultParams() throws Exception {

        final var orders = new ArrayList<Order>();
        for (int i = 0; i < 10; i++) {
            var order = new Order();
            order.setCreatedBy("teste");
            orders.add(order);
        }

        repository.saveAll(orders);

        mockMvc.perform(get("/v1/orders")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath(".page.page").value(0))
                .andExpect(jsonPath(".page.page_size").value(100))
                .andExpect(jsonPath(".page.total_pages").value(1))
                .andExpect(jsonPath(".page.total_elements").value(10))
                .andExpect(jsonPath(".content.length()", is(10)));
    }

    @Test
    @DisplayName("Should list orders successfully with pagination")
    void shouldListOrdersSuccessfullyWithPagination() throws Exception {

        final var orders = new ArrayList<Order>();
        for (int i = 0; i < 10; i++) {
            var order = new Order();
            order.setCreatedBy("teste");
            orders.add(order);
        }

        repository.saveAll(orders);

        mockMvc.perform(get("/v1/orders?page=0&size=2")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath(".page.page").value(0))
                .andExpect(jsonPath(".page.page_size").value(2))
                .andExpect(jsonPath(".page.total_pages").value(5))
                .andExpect(jsonPath(".page.total_elements").value(10))
                .andExpect(jsonPath(".content.length()", is(2)));
    }

}
