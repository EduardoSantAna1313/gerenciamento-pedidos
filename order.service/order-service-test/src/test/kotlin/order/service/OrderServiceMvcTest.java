package order.service;

import static org.junit.jupiter.api.Assertions.*;

import order.DatabaseConfiguration;
import order.OrderServiceApplication;
import order.domain.Order;
import order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import({DatabaseConfiguration.class})
@SpringBootTest(
        classes = {OrderServiceApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
class OrderServiceMvcTest {

    @Autowired
    private OrderService service;

    @Autowired
    private OrderRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Save batch")
    void shouldSaveBatch() {

        for (int i = 0; i < 10; i++) {
            var order = new Order();
            order.setCreatedBy("teste");
            service.save(order);
        }

        assertEquals(10, repository.count());
    }

}
