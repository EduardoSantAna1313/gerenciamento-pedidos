/* (C)2024 */
package br.com.edu.order.infra.rest.v1;

import br.com.edu.order.domain.errors.InvalidPriceException;
import br.com.edu.order.domain.errors.InvalidQuantityException;
import br.com.edu.order.domain.errors.OrderNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(value = OrderNotFoundException.class)
    public ResponseEntity<?> handleNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = InvalidPriceException.class)
    public ResponseEntity<?> handleInvalidPriceException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(value = InvalidQuantityException.class)
    public ResponseEntity<?> handleInvalidQuantityException() {
        return ResponseEntity.badRequest().build();
    }
}
