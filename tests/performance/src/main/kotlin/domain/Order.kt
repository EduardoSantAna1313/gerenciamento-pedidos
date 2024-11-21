package br.com.edu.domain

import com.fasterxml.jackson.annotation.JsonProperty
import domain.Converter
import domain.Item
import domain.Status
import java.time.LocalDateTime
import java.util.*


data class Order(

    val id: UUID = UUID.randomUUID(),

    val status: Status = Status.PENDING,

    @field:JsonProperty("created_by")
    val createdBy: String?,

    val created: LocalDateTime = LocalDateTime.now(),

    @field:JsonProperty("updated_by")
    val updatedBy: String? = null,

    val updated: LocalDateTime = LocalDateTime.now(),

    val items: MutableList<Item>
) {

    fun toJson(): String {
        val mapper = Converter.mapper
        return mapper.writeValueAsString(this)
    }

}