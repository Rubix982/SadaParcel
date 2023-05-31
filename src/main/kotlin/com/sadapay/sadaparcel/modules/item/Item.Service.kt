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

package com.sadapay.sadaparcel.modules.item

import com.sadapay.sadaparcel.modules.item.contract.ItemsDto
import com.sadapay.sadaparcel.modules.models.entities.Item
import com.sadapay.sadaparcel.modules.models.repositories.interfaces.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ItemService(
    @Autowired
    val itemRepository: ItemRepository
) {
    fun findAll(): ItemsDto = ItemsDto(itemRepository.findAll())

    fun findByItemId(itemId: String): Optional<Item> {
        return itemRepository.findByItemId(itemId)
    }

    fun count(): Long {
        return itemRepository.count()
    }

    fun countByItemIds(itemIds: List<String>): Long {
        return itemRepository.countByItemIds(itemIds)
    }

    fun save(item: Item): Item {
        return itemRepository.save(item)
    }

    fun deleteByItemIds(itemIds: List<String>) {
        itemRepository.deleteByItemIds(itemIds)
    }
}
