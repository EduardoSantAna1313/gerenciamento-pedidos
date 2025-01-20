package order.domain.errors

import java.math.BigDecimal
import java.text.MessageFormat

class InvalidPriceException(price: BigDecimal = BigDecimal.ZERO) :
    RuntimeException(MessageFormat.format("The price {0} must be greater than zero.", price))
