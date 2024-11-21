package br.com.edu.order.infra.jobs;

import br.com.edu.order.application.service.OrderService;
import br.com.edu.order.domain.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

@Slf4j
@Component
public class OrderJob {

	private static final int MAX_POOL = 10;

	private final SqsClient sqsClient;

	private final OrderService service;

	private final ObjectMapper mapper;

	private String queueUrl;

	public OrderJob(final SqsClient sqsClient, final OrderService service, final ObjectMapper mapper,
					@Value("${aws.sqs.queue-url}") final String queueUrl) {
		this.service = service;
		this.mapper = mapper;
		this.queueUrl = queueUrl;
		this.sqsClient = sqsClient;
	}

	@Scheduled(fixedRate = 1000)
	public void listeningQueue() {

		log.info("Reading messages from queue {}", queueUrl);

		final ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
				.queueUrl(queueUrl)
				.maxNumberOfMessages(MAX_POOL)
				.build();

		final var response = sqsClient.receiveMessage(receiveRequest);

		if (response == null || !response.hasMessages()) {
			log.info("Nenhuma mensagem na fila!");
			return;
		}

		final var messages = response.messages();

		final var orders = messages.stream().map(this::processMessage).toList();

		service.saveAll(orders);
	}

	private Order processMessage(final Message message) {
		log.debug("Processando a mensagem {}", message.body());

		try {
			final var order = mapper.readValue(message.body(), Order.class);

			sqsClient.deleteMessage(builder -> builder
					.queueUrl(queueUrl)
					.receiptHandle(message.receiptHandle())
			);

			return order;
		} catch (final Exception error) {
			log.error("Ocorreu um erro ao processar a mensagem {}", message, error);
			// TODO send to DLQ
			throw new RuntimeException(error);
		}

	}
}
