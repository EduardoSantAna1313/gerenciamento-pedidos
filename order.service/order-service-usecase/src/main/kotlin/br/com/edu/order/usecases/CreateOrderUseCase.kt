package br.com.edu.order.usecases

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import br.com.edu.order.domain.Order
import br.com.edu.order.ports.MessageRequest
import br.com.edu.order.ports.QueuePort
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreateOrderUseCase(
    private val queuePort: QueuePort,
    private val mapper: ObjectMapper
) {

    fun execute(request: Order): UUID {
        try {
            val messageBody = mapper.writeValueAsString(request)

            val sendMessageRequest = MessageRequest(
                messageBody, GROUP_ID, UUID.randomUUID().toString()
            )

            // Envia a mensagem para a fila
            queuePort.sendMessage(sendMessageRequest)

            return request.id
        } catch (error: JsonProcessingException) {
            log.error("Ocorreu um erro ao gerar mensagem na fila sqs.", error)
            throw RuntimeException(error)
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(CreateOrderUseCase::class.java)

        private const val GROUP_ID = "OrdersGroup"
    }
}