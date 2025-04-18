package br.com.edu.order.infra.listener

import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.sqs.annotation.SqsListener
import br.com.edu.order.domain.Order
import br.com.edu.order.service.OrderService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.sqs.SqsClient

@Component
class SqsQueueListener(
    private val sqsClient: SqsClient,

    private val service: OrderService,

    private val mapper: ObjectMapper
) {

    private val logger: Logger = LoggerFactory.getLogger(SqsQueueListener::class.java)


    @SqsListener("\${aws.sqs.queue-url}")
    fun handle(message: String) {
        logger.debug("Reading message {} from queue.", message)

        val order = mapper.readValue(message, Order::class.java)

        service.save(order)
    }

}