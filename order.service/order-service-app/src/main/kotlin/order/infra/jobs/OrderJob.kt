package order.infra.jobs

import com.fasterxml.jackson.databind.ObjectMapper
import order.domain.Order
import order.service.OrderService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest
import software.amazon.awssdk.services.sqs.model.Message
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest

@Component
class OrderJob(
    private val sqsClient: SqsClient,

    private val service: OrderService,

    private val mapper: ObjectMapper,

    @param:Value("\${aws.sqs.queue-url}")
    private val queueUrl: String
) {

    @Scheduled(fixedRate = 1000)
    fun listeningQueue() {
        log.info("Reading messages from queue {}", queueUrl)

        val receiveRequest = ReceiveMessageRequest.builder()
            .queueUrl(queueUrl)
            .maxNumberOfMessages(MAX_POOL)
            .build()

        val response = sqsClient.receiveMessage(receiveRequest)

        if (response == null || !response.hasMessages()) {
            log.info("Nenhuma mensagem na fila!")
            return
        }

        val messages = response.messages()

        val orders = messages.map { message: Message -> this.processMessage(message) }

        service.saveAll(orders)
    }

    private fun processMessage(message: Message): Order {
        log.debug("Processando a mensagem {}", message.body())

        try {
            val order = mapper.readValue(message.body(), Order::class.java)

            sqsClient.deleteMessage { builder: DeleteMessageRequest.Builder ->
                builder
                    .queueUrl(queueUrl)
                    .receiptHandle(message.receiptHandle())
            }

            return order
        } catch (error: Exception) {
            log.error("Ocorreu um erro ao processar a mensagem {}", message, error)
            // TODO send to DLQ
            throw RuntimeException(error)
        }
    }

    companion object {
        private const val MAX_POOL = 10

        private val log: Logger = LoggerFactory.getLogger(OrderJob::class.java)
    }

}
