/*
 * Copyright 2023, Saif Ul Islam @ SadaParcel, Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the “Software”), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.sadapay.sadaparcel.modules.itemsmanagement

import com.sadapay.sadaparcel.modules.item.ItemDto
import com.sadapay.sadaparcel.modules.models.entities.EntityWithLogs
import com.sadapay.sadaparcel.modules.models.entities.Item
import com.sadapay.sadaparcel.modules.models.repositories.ItemRepository
import com.sadapay.sadaparcel.modules.transformations.TransformationMonadComposer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.Serializable
import javax.transaction.Transactional
import com.sadapay.sadaparcel.modules.itemsmanagement.ItemsManagementLogger.Companion as logger

@Service
class ItemsManagementService(
    @Autowired
    val itemRepository: ItemRepository
) {
    fun findAll(): MutableIterable<Item?> {
        return itemRepository.findAll()
    }

    @Transactional
    fun save(entity: EntityWithLogs<Serializable?>): EntityWithLogs<Serializable?> {
        val itemDto = entity.entity as ItemDto
        val existingItem = itemRepository.findByItemId(itemDto.id).orElse(null)
        val item = existingItem?.apply {
            logger.logApplyingChanges(entity, itemDto)

            name = itemDto.name
            description = itemDto.description
            price = itemDto.price
            cost = itemDto.cost

            (entity.configProcessor as ItemsManagementMonadProcessor).wasAnItemUpdated = true
        }
            ?: Item(itemDto).apply {
                logger.logNewItem(entity, itemDto)
                id = itemRepository.count() + 1
            }

        val savedItem: Item = itemRepository.save(item)
        logger.logSavedItem(entity, savedItem)
        return TransformationMonadComposer.wrapWithLogs(savedItem)
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