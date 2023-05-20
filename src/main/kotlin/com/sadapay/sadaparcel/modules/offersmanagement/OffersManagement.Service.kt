package com.sadapay.sadaparcel.modules.offersmanagement

import com.sadapay.sadaparcel.modules.models.entities.Offer
import com.sadapay.sadaparcel.modules.models.repositories.OfferRepository
import com.sadapay.sadaparcel.modules.offer.OfferDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class OffersManagementService(
    @Autowired
    val offerRepository: OfferRepository
) {
    fun findAll(): MutableIterable<Offer?> {
        return offerRepository.findAll()
    }

    fun save(offerDto: OfferDto): Offer {

        var offer: Optional<Offer> = offerRepository.findByItemId(offerDto.itemId)

        if (offer.isEmpty) {
            offer = Optional.of(Offer(offerDto))
        } else {
            offer.get().name = offerDto.name
            offer.get().description = offerDto.description
            offer.get().priceReduction = offerDto.priceReduction
            offer.get().quantityThreshold = offerDto.quantityThreshold
        }

        return offerRepository.save(offer.get())
    }

    fun areAllIdsValid(offerIds: List<String>): Boolean {
        val count: Long = offerRepository.countByOfferIds(offerIds)
        return count == offerIds.size.toLong()
    }

    fun deleteOffers(offerIds: List<String>) {
        offerRepository.deleteByOfferIds(offerIds)
    }
}