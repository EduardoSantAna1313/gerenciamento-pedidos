package br.com.edu.order.application.service;

import br.com.edu.order.domain.Order;
import br.com.edu.order.domain.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(final OrderRepository repository) {
        this.repository = repository;
    }

    public void save(final Order order) {
        var id = repository.save(order);
        log.info("Saved order {}", id);
    }

    public void saveAll(final List<Order> orders) {
        log.info("Batch insert {} orders", orders.size());

        orders.forEach(Order::calculateTotal);

        var saved = repository.saveAll(orders);
        log.info("Saved {} orders", saved);
        log.info("Qty saved {} orders", saved.size());
    }
}
