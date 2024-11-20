package br.com.edu.order.application.usecases;

import br.com.edu.order.domain.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.UUID;

@Slf4j
@Service
public class CreateOrderUseCase {

    private static final String GROUP_ID = "OrdersGroup";

    private final SqsClient sqsClient;

    private final String queueUrl;

    private final ObjectMapper mapper;

    public CreateOrderUseCase(SqsClient sqsClient, @Value("${aws.sqs.queue-url}") String queueUrl, ObjectMapper mapper) {
        this.sqsClient = sqsClient;
        this.queueUrl = queueUrl;
        this.mapper = mapper;
    }

    public UUID execute(final Order request) {

        try {

            final var messageBody = mapper.writeValueAsString(request);

            final var sendMessageRequest = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(messageBody)
                    .messageGroupId(GROUP_ID)
                    .messageDeduplicationId(UUID.randomUUID().toString())
                    .build();

            // Envia a mensagem para a fila
            sqsClient.sendMessage(sendMessageRequest);

            return request.getId();
        } catch (final JsonProcessingException error) {
            log.error("Ocorreu um erro ao gerar mensagem na fila sqs.", error);
            throw new RuntimeException(error);
        }
    }
}



