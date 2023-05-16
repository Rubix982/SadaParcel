package com.sadapay.sadaparcel.modules.item

import com.sadapay.sadaparcel.modules.models.entities.Item
import com.sadapay.sadaparcel.modules.models.repositories.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ItemService(
    @Autowired
    val itemRepository: ItemRepository
) {
    fun findAll(): MutableIterable<Item?> {
        return itemRepository.findAll()
    }
}
