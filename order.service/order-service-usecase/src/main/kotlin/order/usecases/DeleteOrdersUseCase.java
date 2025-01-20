package order.usecases;

import order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteOrdersUseCase {

    private final OrderRepository repository;

    public DeleteOrdersUseCase(OrderRepository repository) {
        this.repository = repository;
    }

    public void execute() {
        repository.deleteAll();
    }
}
