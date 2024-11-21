package domain

import java.math.BigDecimal
import java.util.*

data class Item(
    val id: UUID = UUID.randomUUID(),
    val product: String,
    val price: BigDecimal,
    val quantity: Int = 1
)