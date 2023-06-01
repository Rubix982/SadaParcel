/*
 * Copyright 2023, Saif Ul Islam @ SadaParcel, Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the “Software”), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.sadapay.sadaparcel.modules.offersmanagement

import com.sadapay.sadaparcel.modules.models.entities.Offer
import com.sadapay.sadaparcel.modules.models.repositories.interfaces.OfferRepository
import com.sadapay.sadaparcel.modules.offer.contract.OfferDto
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