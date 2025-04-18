package br.com.edu.order.ports

data class MessageRequest (
    val messageBody: String,
    val groupId: String,
    val deduplicationId: String
)