package com.sadapay.sadaparcel.modules.offer

import com.sadapay.sadaparcel.modules.models.entities.Offer
import com.sadapay.sadaparcel.modules.models.repositories.OfferRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OfferService(
    @Autowired
    val offerRepository: OfferRepository
) {
    fun findAll(): MutableIterable<Offer?> {
        return offerRepository.findAll()
    }
}