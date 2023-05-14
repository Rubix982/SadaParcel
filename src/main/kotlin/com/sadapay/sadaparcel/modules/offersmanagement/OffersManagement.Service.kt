package com.sadapay.sadaparcel.modules.offersmanagement

import com.sadapay.sadaparcel.modules.models.entities.Offer
import com.sadapay.sadaparcel.modules.models.repositories.OfferRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OffersManagementService(
    @Autowired
    val offerRepository: OfferRepository
) {
    fun list(): List<Offer?>? {
        return offerRepository.findAll()
    }
}