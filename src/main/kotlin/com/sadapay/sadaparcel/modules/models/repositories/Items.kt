package com.sadapay.sadaparcel.modules.models.repositories

import com.sadapay.sadaparcel.modules.models.entities.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : CrudRepository<Item?, Long?>, JpaRepository<Item?, Long?>,
    PagingAndSortingRepository<Item?, Long?>