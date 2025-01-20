package order.ports

fun interface QueuePort {

    fun sendMessage(messageRequest: MessageRequest)

}
