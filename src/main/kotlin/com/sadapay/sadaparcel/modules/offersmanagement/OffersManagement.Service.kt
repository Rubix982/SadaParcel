package com.sadapay.sadaparcel.modules.offersmanagement

import com.sadapay.sadaparcel.modules.models.entities.Offer
import com.sadapay.sadaparcel.modules.models.repositories.OfferRepository
import com.sadapay.sadaparcel.modules.offer.OfferDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class OffersManagementService(
    @Autowired
    val offerRepository: OfferRepository
) {
    fun findAll(): MutableIterable<Offer?> {
        return offerRepository.findAll()
    }

    @Transactional
    fun save(offerDto: OfferDto): Offer {
        return offerRepository.findByItemId(offerDto.itemId)
            .orElseGet { offerRepository.save(Offer(offerDto)) }
            .apply {
                name = offerDto.name
                description = offerDto.description
                priceReduction = offerDto.priceReduction
                quantityThreshold = offerDto.quantityThreshold
                offerRepository.save(this)
            }
    }

    fun areAllIdsValid(offerIds: List<String>): Boolean {
        val count: Long = offerRepository.countByOfferIds(offerIds)
        return count == offerIds.size.toLong()
    }

    @Transactional
    fun deleteOffers(offerIds: List<String>) {
        offerRepository.deleteByOfferIds(offerIds)
    }
}