/* (C)2024 */
package br.com.edu.order.application.usecases;

import br.com.edu.order.domain.repository.OrderRepository;
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
