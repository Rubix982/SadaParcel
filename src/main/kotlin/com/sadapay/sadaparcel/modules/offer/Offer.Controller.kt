package com.sadapay.sadaparcel.modules.offer

import com.sadapay.sadaparcel.modules.models.entities.Offer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class OffersController @Autowired constructor(offerService: OfferService?) {
    private val offerService: OfferService?

    init {
        this.offerService = offerService
    }

    @GetMapping("/offers", produces = ["application/json"])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun getOffers(): MutableIterable<Offer?>? = offerService?.findAll()
}