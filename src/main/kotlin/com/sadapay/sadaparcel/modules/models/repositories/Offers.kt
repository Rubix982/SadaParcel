package com.sadapay.sadaparcel.modules.models.repositories

import com.sadapay.sadaparcel.modules.models.entities.Offer
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OfferRepository : CrudRepository<Offer?, Long?> {

    fun findByItemId(itemId: String): Optional<Offer>

    @Query("SELECT COUNT(*) FROM Offer offer WHERE offer.itemId IN :offerIds")
    fun countByOfferIds(offerIds: List<String>): Long

    @Modifying
    @Query("DELETE FROM Offer WHERE itemId IN :offerIds")
    fun deleteByOfferIds(offerIds: List<String>)
}