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

        fun deletedItems(entity: EntityWithLogs<Serializable?>, itemIds: List<String>) {
            entity.addToLogs(String.format(ItemsManagementConstants.ITEMS_DELETED, itemIds))
        }
    }
}