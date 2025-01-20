package order.domain.errors

import java.text.MessageFormat

class InvalidQuantityException(quantity: Int) :
    RuntimeException(MessageFormat.format("The quantity {0} must be greater than zero.", quantity))
