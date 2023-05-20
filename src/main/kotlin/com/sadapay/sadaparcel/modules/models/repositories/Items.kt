package com.sadapay.sadaparcel.modules.models.repositories

import com.sadapay.sadaparcel.modules.models.entities.Item
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : CrudRepository<Item?, Long?> {
    fun findByItemId(itemId: String): Item?

    @Query("SELECT COUNT() FROM Item i WHERE i.itemId IN :itemIds")
    fun countByItemIds(itemIds: List<String>): Long

    @Modifying
    @Query("DELETE FROM Item WHERE itemId IN :itemIds")
    fun deleteByItemIds(itemIds: List<String>)
}