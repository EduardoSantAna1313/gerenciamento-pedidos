package order.adapters.queue

import order.ports.MessageRequest
import order.ports.QueuePort
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import java.util.*

@Service
class SqsQueuePort(
    private val sqsClient: SqsClient
) : QueuePort {

    @Value("\${aws.sqs.queue-url}")
    lateinit var queueUrl: String

    override fun sendMessage(messageRequest: MessageRequest) {

        val sendMessageRequest = SendMessageRequest.builder()
            .queueUrl(queueUrl)
            .messageBody(messageRequest.messageBody)
            .messageGroupId(messageRequest.groupId)
            .messageDeduplicationId(UUID.randomUUID().toString())
            .build();

        // Envia a mensagem para a fila
        sqsClient.sendMessage(sendMessageRequest);
    }
}