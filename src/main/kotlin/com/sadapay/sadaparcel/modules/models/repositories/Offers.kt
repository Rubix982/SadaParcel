package com.sadapay.sadaparcel.modules.models.repositories

import com.sadapay.sadaparcel.modules.models.entities.Offer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface OfferRepository : CrudRepository<Offer?, Long?>, JpaRepository<Offer?, Long?>,
    PagingAndSortingRepository<Offer?, Long?>