package com.sadapay.sadaparcel.modules.offer

import com.sadapay.sadaparcel.modules.models.repositories.Offers
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RequestMapping("offers")
@RestController
class OffersController {
    @GetMapping(produces = ["application/json"])
    @ResponseBody
    fun getOffers(): List<Offers> = listOf(Offers())
}