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

package com.sadapay.sadaparcel.modules.line

import com.sadapay.sadaparcel.modules.item.ItemDto
import lombok.Getter
import lombok.NonNull
import lombok.RequiredArgsConstructor
import lombok.experimental.Accessors
import java.io.Serializable
import javax.validation.constraints.NotNull

/**
 * A DTO for the {@link com.sadapay.sadaparcel.modules.models.entities.Line} entity
 */
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
data class LinesDto(
    @NotNull
    @NonNull
    var lines: ArrayList<LineDto> = ArrayList()
) : Serializable {
    companion object {
        private const val serialVersionUID = -6773356372607147359L
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LinesDto) return false
        if (lines != other.lines) return false
        return true
    }

    override fun hashCode(): Int {
        return lines.hashCode()
    }

    override fun toString(): String {
        return "LinesDTO{" +
                "lines='$lines', " +
                "}"
    }
}

/**
 * A DTO for the {@link com.sadapay.sadaparcel.modules.models.entities.Line} entity
 */
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
data class LineDto(
    @NotNull
    @NonNull
    val item: ItemDto = ItemDto(),
    @NotNull
    @NonNull
    val quantity: Int = 0
) : Serializable {
    companion object {
        private const val serialVersionUID = -1937216272139075293L
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LineDto) return false
        if (item != other.item) return false
        if (quantity != other.quantity) return false
        return true
    }

    override fun hashCode(): Int {
        var result = item.hashCode()
        result = 31 * result + quantity.hashCode()
        return result
    }

    override fun toString(): String {
        return "LineDTO{" +
                "itemDto='$item', " +
                "quantity='$quantity', " +
                "}"
    }
}