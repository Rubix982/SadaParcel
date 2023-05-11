package com.sadapay.sadaparcel.modules.offersmanagement

import com.sadapay.sadaparcel.modules.models.entities.Offer
import org.springframework.web.bind.annotation.*

@RequestMapping("offers-management")
@RestController
class OffersManagementController {
    @GetMapping(produces = ["application/json"])
    @ResponseBody
    fun getOffersManagement(): List<Offer> =
        listOf(
            Offer("1", "offer1", "OFFER1", "1.0", 1.1, 1),
            Offer("2", "offer2", "OFFER2", "2.0", 1.1, 1)
        )

    @PostMapping(produces = ["application/json"])
    @ResponseBody
    fun addOffer(offer: Offer): Offer = offer

    @DeleteMapping(produces = ["application/json"])
    @ResponseBody
    fun removeOffer(offer: Offer): Offer = offer
}