package order.usecases;

import order.ports.MessageRequest;
import order.ports.QueuePort;
import order.domain.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateOrderUseCase {

    private static final Logger log = LoggerFactory.getLogger(CreateOrderUseCase.class);

    private static final String GROUP_ID = "OrdersGroup";

    private final QueuePort queuePort;

    private final ObjectMapper mapper;

    public CreateOrderUseCase(final QueuePort queuePort,
                              final ObjectMapper mapper) {
        this.queuePort = queuePort;
        this.mapper = mapper;
    }

    public UUID execute(final Order request) {

        try {
            final var messageBody = mapper.writeValueAsString(request);

            final var sendMessageRequest = new MessageRequest(
                    messageBody, GROUP_ID, UUID.randomUUID().toString()
            );

            // Envia a mensagem para a fila
            queuePort.sendMessage(sendMessageRequest);

            return request.getId();
        } catch (final JsonProcessingException error) {
            log.error("Ocorreu um erro ao gerar mensagem na fila sqs.", error);
            throw new RuntimeException(error);
        }
    }
}
