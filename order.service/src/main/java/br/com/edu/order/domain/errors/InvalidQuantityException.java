package br.com.edu.order.domain.errors;

import java.text.MessageFormat;

public class InvalidQuantityException extends RuntimeException {

    public InvalidQuantityException(Integer quantity) {
        super(MessageFormat.format("The quantity {0} must be greater than zero.", quantity));
    }
}
