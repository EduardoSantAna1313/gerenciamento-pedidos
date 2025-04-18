package br.com.edu.order.ports

fun interface QueuePort {

    fun sendMessage(messageRequest: MessageRequest)

}
