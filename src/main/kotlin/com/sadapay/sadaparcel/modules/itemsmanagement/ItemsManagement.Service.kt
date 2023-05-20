package com.sadapay.sadaparcel.modules.itemsmanagement

import com.sadapay.sadaparcel.modules.item.ItemDto
import com.sadapay.sadaparcel.modules.models.entities.Item
import com.sadapay.sadaparcel.modules.models.repositories.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ItemsManagementService(
    @Autowired
    val itemRepository: ItemRepository
) {
    fun findAll(): MutableIterable<Item?> {
        return itemRepository.findAll()
    }

    fun save(itemDto: ItemDto): Item {

        var item: Item? = itemRepository.findByItemId(itemDto.itemId)

        if (item == null) {
            item = Item(itemDto)
        } else {
            item.name = itemDto.name
            item.description = itemDto.description
            item.price = itemDto.price
            item.cost = itemDto.cost
        }

        return itemRepository.save(item)
    }

    fun areAllIdsValid(itemIds: List<String>): Boolean {
        val count: Long = itemRepository.countByItemIds(itemIds)
        return count == itemIds.size.toLong()
    }

    fun deleteItems(itemIds: List<String>) {
        itemRepository.deleteByItemIds(itemIds)
    }
}