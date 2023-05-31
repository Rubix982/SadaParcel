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

package com.sadapay.sadaparcel.modules.item.contract

import com.sadapay.sadaparcel.modules.item.ItemDto
import com.sadapay.sadaparcel.modules.models.entities.Item
import java.io.Serializable

/**
 * A DTO for the {@link com.sadapay.sadaparcel.modules.models.entities.Item} entity
 */
data class ItemsDto(
    val items: MutableList<ItemDto> = mutableListOf()
) : Serializable {
    companion object {
        private const val serialVersionUID = -6773356372607147358L
    }

    constructor(items: MutableIterable<Item?>?) : this() {
        if (items == null) return
        val anyList = items as? List<*> ?: return
        for (i in 0 until anyList.count()) {
            val result: Any = anyList.elementAt(i) ?: continue
            val item = result as? Array<*> ?: continue
            this.items.add(ItemDto(item))
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ItemsDto) return false
        if (items != other.items) return false
        return true
    }

    override fun hashCode(): Int {
        return items.hashCode()
    }

    override fun toString(): String {
        return "ItemsDTO{" +
                "items='$items', " +
                "}"
    }
}