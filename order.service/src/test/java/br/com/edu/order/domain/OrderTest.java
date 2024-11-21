package br.com.edu.order.domain;

import br.com.edu.order.domain.errors.InvalidPriceException;
import br.com.edu.order.domain.errors.InvalidQuantityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    @DisplayName("Should create with default values")
    void shouldCreateDefaultValues() {
        final var order = new Order();
        assertEquals(Status.PENDING, order.getStatus());
        assertNotNull(order.getCreated());
        assertNotNull(order.getUpdated());
        assertNotNull(order.getId());
        assertEquals(BigDecimal.ZERO, order.getTotal());
    }

    @Test
    @DisplayName("Should calculate the total value")
    void shouldCalculateTotal() {

        final var item = new Item();
        item.setQuantity(2);
        item.setProduct("test product");
        item.setPrice(BigDecimal.valueOf(0.01));

        final var order = new Order();
        order.addItem(item);

        var total = order.getTotal();
        assertEquals(BigDecimal.valueOf(0.02), total);
    }

    @Test
    @DisplayName("Should throw InvalidPriceException when the price is zero")
    void shouldThrowInvalidPriceWithPriceZero() {
        assertThrows(InvalidPriceException.class, () -> {
            final var item = new Item();
            item.setQuantity(2);
            item.setProduct("test product");
            item.setPrice(BigDecimal.valueOf(0.00));
        });
    }

    @Test
    @DisplayName("Should throw InvalidPriceException when the price is negative")
    void shouldThrowInvalidPriceWithNegative() {
        assertThrows(InvalidPriceException.class, () -> {
            final var item = new Item();
            item.setQuantity(2);
            item.setProduct("test product");
            item.setPrice(BigDecimal.valueOf(-0.01));
        });
    }

    @Test
    @DisplayName("Should throw InvalidQuantity when the quantity is zero")
    void shouldThrowInvalidQuantityWithPriceZero() {
        assertThrows(InvalidQuantityException.class, () -> {
            final var item = new Item();
            item.setQuantity(0);
            item.setProduct("test product");
            item.setPrice(BigDecimal.valueOf(0.01));
        });
    }

    @Test
    @DisplayName("Should throw InvalidQuantity when the quantity is negative")
    void shouldThrowInvalidQuantityWithNegative() {
        assertThrows(InvalidQuantityException.class, () -> {
            final var item = new Item();
            item.setQuantity(-1);
            item.setProduct("test product");
            item.setPrice(BigDecimal.valueOf(0.01));
        });
    }

}