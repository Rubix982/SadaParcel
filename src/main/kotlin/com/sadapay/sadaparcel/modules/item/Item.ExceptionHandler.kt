package com.sadapay.sadaparcel.modules.item

import com.sadapay.sadaparcel.modules.itemsmanagement.NonExistingItemException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * Maps exceptions to HTTP codes
 */
@RestControllerAdvice
class ItemExceptionHandler {
    @ExceptionHandler(NonExistingItemException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNonExistingHero() {
    }
}