package com.sadapay.sadaparcel.modules.offersmanagement

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * Maps exceptions to HTTP codes
 */
@RestControllerAdvice
class OffersManagementExceptionHandler {
    @ExceptionHandler(NonExistingOfferException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNonExistingOffer() {
    }
}