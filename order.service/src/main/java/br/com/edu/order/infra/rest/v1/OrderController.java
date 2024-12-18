package br.com.edu.order.infra.rest.v1;

import br.com.edu.order.application.usecases.CreateOrderUseCase;
import br.com.edu.order.application.usecases.DetailOrdersUseCase;
import br.com.edu.order.application.usecases.ListOrdersUseCase;
import br.com.edu.order.domain.Order;
import br.com.edu.order.domain.errors.OrderNotFoundException;
import br.com.edu.order.infra.rest.v1.request.OrderRequest;
import br.com.edu.order.infra.rest.v1.response.ListResponse;
import br.com.edu.order.infra.rest.v1.response.OrderResponse;
import br.com.edu.order.infra.rest.v1.response.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    private final ListOrdersUseCase listOrdersUseCase;

    private final DetailOrdersUseCase detailOrdersUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase, ListOrdersUseCase listOrdersUseCase, DetailOrdersUseCase detailOrdersUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.listOrdersUseCase = listOrdersUseCase;
        this.detailOrdersUseCase = detailOrdersUseCase;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody OrderRequest request) {

        final var input = new Order();
        input.setCreatedBy(request.getCreatedBy());
        input.setItems(request.toModelItems());

        final var id = createOrderUseCase.execute(input);

        return ResponseEntity.created(URI.create("/v1/orders/" + id)).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(value = "size", required = false, defaultValue = "100") Integer pageSize) {
        final var result = listOrdersUseCase.execute(page, pageSize);

        final var listResponse = ListResponse.builder()
                .content(result.getContent().stream().map(OrderResponse::fromModel).toList())
                .page(new Page(result.getNumber(), result.getSize(), result.getTotalPages(), result.getTotalElements()))
                .build();

        return ResponseEntity.ok(listResponse);
    }

    @GetMapping(value = "/{order_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> detail(@PathVariable("order_id") String orderId) {
        final var result = detailOrdersUseCase.execute(orderId).orElseThrow(OrderNotFoundException::new);

        final var response = OrderResponse.fromModel(result);

        return ResponseEntity.ok(response);
    }

}
