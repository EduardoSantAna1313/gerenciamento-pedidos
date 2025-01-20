package order.domain

import order.domain.errors.InvalidPriceException
import order.domain.errors.InvalidQuantityException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.RoundingMode

internal class OrderTest {

    @Test
    @DisplayName("Should create with default values")
    fun shouldCreateDefaultValues() {
        val order = Order()
        assertEquals(Status.PENDING, order.status)
        assertNotNull(order.created)
        assertNotNull(order.updated)
        assertNotNull(order.id)
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), order.total)
    }

    @Test
    @DisplayName("Should calculate the total value")
    fun shouldCalculateTotal() {
        val item = Item()
        item.quantity = 2
        item.product = "test product"
        item.price = BigDecimal.valueOf(0.01)

        val order = Order()
        order.addItem(item)

        val total = order.total
        assertEquals(BigDecimal.valueOf(0.02), total)
    }

    @Test
    @DisplayName("Should throw InvalidPriceException when the price is zero")
    fun shouldThrowInvalidPriceWithPriceZero() {
        assertThrows(InvalidPriceException::class.java) {
            val item = Item()
            item.quantity = 2
            item.product = "test product"
            item.price = BigDecimal.valueOf(0.00)
        }
    }

    @Test
    @DisplayName("Should throw InvalidPriceException when the price is negative")
    fun shouldThrowInvalidPriceWithNegative() {
        assertThrows(InvalidPriceException::class.java) {
            val item = Item()
            item.quantity = 2
            item.product = "test product"
            item.price = BigDecimal.valueOf(-0.01)
        }
    }

    @Test
    @DisplayName("Should throw InvalidQuantity when the quantity is zero")
    fun shouldThrowInvalidQuantityWithPriceZero() {
        assertThrows(InvalidQuantityException::class.java) {
            val item = Item()
            item.quantity = 0
            item.product = "test product"
            item.price = BigDecimal.valueOf(0.01)
        }
    }

    @Test
    @DisplayName("Should throw InvalidQuantity when the quantity is negative")
    fun shouldThrowInvalidQuantityWithNegative() {
        assertThrows(InvalidQuantityException::class.java) {
            val item = Item()
            item.quantity = -1
            item.product = "test product"
            item.price = BigDecimal.valueOf(0.01)
        }
    }

}
