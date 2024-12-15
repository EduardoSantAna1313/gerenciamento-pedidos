/* (C)2024 */
package br.com.edu.order.domain.errors;

import java.math.BigDecimal;
import java.text.MessageFormat;

public class InvalidPriceException extends RuntimeException {

    public InvalidPriceException(BigDecimal price) {
        super(MessageFormat.format("The price {0} must be greater than zero.", price));
    }
}
