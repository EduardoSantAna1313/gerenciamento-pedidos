package br.com.edu.order.infra.jobs;

import br.com.edu.order.application.service.OrderService;
import br.com.edu.order.domain.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Slf4j
@Component
public class OrderJob {

	private static final int MAX_POOL = 10;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	private final SqsClient sqsClient;

	private final OrderService service;

	private final ObjectMapper mapper;

	private String queueUrl;

	public OrderJob(SqsClient sqsClient, OrderService service, ObjectMapper mapper,
					@Value("${aws.sqs.queue-url}") String queueUrl) {
		this.service = service;
		this.mapper = mapper;
		this.queueUrl = queueUrl;
		this.sqsClient = sqsClient;
	}

	@Scheduled(fixedRate = 5000)
	public void listeningQueue() {
		log.info("Reading messages from queue {}", queueUrl);

		final var receiveRequest = ReceiveMessageRequest.builder()
				.queueUrl(queueUrl)
				.maxNumberOfMessages(MAX_POOL)
				.waitTimeSeconds(5)
				.build();

		final var response = sqsClient.receiveMessage(receiveRequest);

		if (!response.hasMessages()) {
			log.info("Nenhuma mensagem na fila!");
			return ;
		}

		final var messages = response.messages();

		final var orders = new ArrayList<Order>();
		for (final var message : messages) {

			log.info("Processando a mensagem {}", message);

			try {
				final var order = mapper.readValue(message.body(), Order.class);
				orders.add(order);
			} catch (final Exception error) {
				log.error("Ocorreu um erro ao processar a mensagem {}", message, error);
				// TODO send to DLQ
			}
		}

		service.saveAll(orders);

	}
}