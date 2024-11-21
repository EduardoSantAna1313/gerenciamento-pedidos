package br.com.edu.order.application.service;

import br.com.edu.order.DatabaseConfiguration;
import br.com.edu.order.domain.Order;
import br.com.edu.order.domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Import(DatabaseConfiguration.class)
@SpringBootTest(
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

        final var orders = new ArrayList<Order>();
        for (int i = 0; i < 10; i++) {
            var order = new Order();
            order.setCreatedBy("teste");
            orders.add(order);
        }

        service.saveAll(orders);

        assertEquals(10, repository.count());
    }

}