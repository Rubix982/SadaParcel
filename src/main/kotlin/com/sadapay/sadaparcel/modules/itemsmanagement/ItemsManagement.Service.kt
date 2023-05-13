package com.sadapay.sadaparcel.modules.itemsmanagement

import com.sadapay.sadaparcel.modules.models.entities.Item
import com.sadapay.sadaparcel.modules.models.repositories.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ItemsManagementService(
    @Autowired
    val itemRepository: ItemRepository
) {
    fun list(): List<Item?>? {
        return itemRepository.findAll()
    }
}