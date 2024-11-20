package br.com.edu.order.application.usecases;

import br.com.edu.order.domain.Order;
import br.com.edu.order.domain.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ListOrdersUseCase {

    private final OrderRepository repository;

    public ListOrdersUseCase(final OrderRepository repository) {
        this.repository = repository;
    }

    public Page<Order> execute(int page, int pageSize) {
        return repository.findAll(PageRequest.of(page, pageSize));
    }

}
