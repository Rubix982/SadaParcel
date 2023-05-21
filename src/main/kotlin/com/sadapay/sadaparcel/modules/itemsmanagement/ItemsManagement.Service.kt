package com.sadapay.sadaparcel.modules.itemsmanagement

import com.sadapay.sadaparcel.modules.item.ItemDto
import com.sadapay.sadaparcel.modules.models.entities.Item
import com.sadapay.sadaparcel.modules.models.repositories.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ItemsManagementService(
    @Autowired
    val itemRepository: ItemRepository
) {
    fun findAll(): MutableIterable<Item?> {
        return itemRepository.findAll()
    }

    fun save(itemDto: ItemDto): Item {

        var item: Optional<Item> = itemRepository.findByItemId(itemDto.id)

        if (item.isEmpty) {
            item = Optional.of(Item(itemDto))
            item.get().id = itemRepository.findAll().count() + 1
        } else {
            item.get().name = itemDto.name
            item.get().description = itemDto.description
            item.get().price = itemDto.price
            item.get().cost = itemDto.cost
        }

        return itemRepository.save(item.get())
    }

    fun areAllIdsValid(itemIds: List<String>): Boolean {
        val count: Long = itemRepository.countByItemIds(itemIds)
        return count == itemIds.size.toLong()
    }

    fun deleteItems(itemIds: List<String>) {
        itemRepository.deleteByItemIds(itemIds)
    }
}