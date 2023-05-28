package com.sadapay.sadaparcel.modules.itemsmanagement

import com.sadapay.sadaparcel.modules.item.ItemDto
import com.sadapay.sadaparcel.modules.models.entities.EntityWithLogs
import com.sadapay.sadaparcel.modules.models.entities.Item
import java.io.Serializable

class ItemsManagementLogger {
    companion object {
        fun logApplyingChanges(entity: EntityWithLogs<Serializable?>, itemDto: ItemDto) {
            entity.addToLogs(
                String.format(
                    ItemsManagementConstants.EXISTING_ITEM_APPLYING_CHANGES,
                    itemDto.id,
                    this,
                    itemDto
                )
            )
        }

        fun logNewItem(entity: EntityWithLogs<Serializable?>, itemDto: ItemDto) {
            entity.addToLogs(String.format(ItemsManagementConstants.NEW_ITEM_CREATED, itemDto.id, itemDto))
        }

        fun logSavedItem(entity: EntityWithLogs<Serializable?>, savedItem: Item) {
            entity.addToLogs(String.format(ItemsManagementConstants.ITEM_SAVED, savedItem))
        }
    }
}