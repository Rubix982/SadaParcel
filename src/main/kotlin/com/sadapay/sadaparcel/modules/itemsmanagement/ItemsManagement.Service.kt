package com.sadapay.sadaparcel.modules.itemsmanagement

import com.sadapay.sadaparcel.modules.item.ItemDto
import com.sadapay.sadaparcel.modules.models.entities.Item
import com.sadapay.sadaparcel.modules.models.repositories.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ItemsManagementService(
    @Autowired
    val itemRepository: ItemRepository
) {
    fun findAll(): MutableIterable<Item?> {
        return itemRepository.findAll()
    }

    @Transactional
    fun save(itemDto: ItemDto): Item {
        val item = itemRepository.findByItemId(itemDto.id)
            .orElseGet {
                val temporaryItem = Item(itemDto)
                temporaryItem.id = itemRepository.findAll().count() + 1
                itemRepository.save(temporaryItem)
            }
            .apply {
                name = itemDto.name
                description = itemDto.description
                price = itemDto.price
                cost = itemDto.cost
                itemRepository.save(this)
            }
        return item
    }

    fun areAllIdsValid(itemIds: List<String>): Boolean {
        val count: Long = itemRepository.countByItemIds(itemIds)
        return count == itemIds.size.toLong()
    }

    @Transactional
    fun deleteItems(itemIds: List<String>) {
        itemRepository.deleteByItemIds(itemIds)
    }
}