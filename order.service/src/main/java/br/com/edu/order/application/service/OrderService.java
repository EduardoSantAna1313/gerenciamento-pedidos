/* (C)2024 */
package br.com.edu.order.application.service;

import br.com.edu.order.domain.Order;
import br.com.edu.order.domain.repository.OrderRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(final OrderRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void save(final Order order) {
        var saved = repository.save(order);
        log.info("Saved order {}", saved.getId());
    }

    @Transactional
    public void saveAll(final List<Order> orders) {
        log.info("Inserting {} orders", orders.size());
        orders.forEach(Order::calculateTotal);
        orders.forEach(Order::changeProcessed);

        final var saved = repository.saveAll(orders);
        log.info("Saved {} orders", saved);
    }
}
