package order.usecases;

import order.domain.Order;
import order.repository.OrderRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

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
