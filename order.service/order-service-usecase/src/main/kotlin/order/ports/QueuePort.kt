package order.ports

interface QueuePort {

    fun sendMessage(messageRequest: MessageRequest)

}

data class MessageRequest (
    val messageBody: String,
    val groupId: String,
    val deduplicationId: String
)