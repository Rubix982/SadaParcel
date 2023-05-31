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

import com.sadapay.sadaparcel.modules.models.entities.Item
import lombok.Getter
import lombok.NonNull
import lombok.RequiredArgsConstructor
import lombok.experimental.Accessors
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * A DTO for the {@link com.sadapay.sadaparcel.modules.models.entities.Item} entity
 */
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
data class ItemDto(
    @NotBlank
    @NotNull
    @NonNull
    var id: String = "",
    @NotNull
    @NonNull
    var name: String = "",
    @NotNull
    @NonNull
    var description: String = "",
    @NotNull
    @NonNull
    var price: Double = 0.0,
    @NotNull
    @NonNull
    var cost: Double = 0.0
) : Serializable {
    companion object {
        private const val serialVersionUID = -6773356372607147358L
    }

    constructor(item: Item) : this(item.itemId, item.name, item.description, item.price, item.cost)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ItemDto) return false
        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (price != other.price) return false
        if (cost != other.cost) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + cost.hashCode()
        return result
    }

    override fun toString(): String {
        return "ItemDTO{" +
                "id='$id', " +
                "name='$name', " +
                "description='$description', " +
                "price=$price, " +
                "cost=$cost" +
                '}'
    }
}