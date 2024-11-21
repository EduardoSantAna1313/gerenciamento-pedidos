package br.com.edu.order.application.usecases;

import br.com.edu.order.domain.Order;
import br.com.edu.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DetailOrdersUseCase {

    private final OrderRepository repository;

    public DetailOrdersUseCase(final OrderRepository repository) {
        this.repository = repository;
    }

    public Optional<Order> execute(String orderid) {
        return repository.findById(UUID.fromString(orderid));
    }


}
