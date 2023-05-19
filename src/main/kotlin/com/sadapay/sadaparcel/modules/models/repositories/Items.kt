package com.sadapay.sadaparcel.modules.models.repositories

import com.sadapay.sadaparcel.modules.models.entities.Item
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : CrudRepository<Item?, Long?> {
    fun findByItemId(itemId: String): Item?
}