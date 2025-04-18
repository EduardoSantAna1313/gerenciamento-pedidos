package br.com.edu.order.infra.rest.v1.handler

import br.com.edu.order.domain.errors.InvalidPriceException
import br.com.edu.order.domain.errors.InvalidQuantityException
import br.com.edu.order.domain.errors.OrderNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class DefaultExceptionHandler {
    @ExceptionHandler(value = [OrderNotFoundException::class])
    fun handleNotFound(): ResponseEntity<*> {
        return ResponseEntity.notFound().build<Any>()
    }

    @ExceptionHandler(value = [InvalidPriceException::class])
    fun handleInvalidPriceException(): ResponseEntity<*> {
        return ResponseEntity.badRequest().build<Any>()
    }

    @ExceptionHandler(value = [InvalidQuantityException::class])
    fun handleInvalidQuantityException(): ResponseEntity<*> {
        return ResponseEntity.badRequest().build<Any>()
    }

}
