package br.com.edu.order.domain

import br.com.edu.order.domain.errors.InvalidPriceException
import br.com.edu.order.domain.errors.InvalidQuantityException
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "Items")
class Item(

    @Id
    @Column(columnDefinition = "uuid")
    var id: UUID = UUID.randomUUID(),

    @Column(name = "order_id", columnDefinition = "uuid")
    var orderId: UUID? = null,

    @Column(name = "product")
    var product: String? = null

) {

    @Column(name = "quantity")
    var quantity: Int = 0
        set(value) {
            if (value <= 0) {
                throw InvalidQuantityException(value)
            }
            field = value
        }

    @Column(name = "price")
    var price: BigDecimal = BigDecimal.ZERO
        set(value) {
            if (value.compareTo(BigDecimal.ZERO) < 1) {
                throw InvalidPriceException(value)
            }
            field = value
        }

    fun totalItem(): BigDecimal {
        return price.multiply(BigDecimal.valueOf(quantity.toLong()))
    }
}
