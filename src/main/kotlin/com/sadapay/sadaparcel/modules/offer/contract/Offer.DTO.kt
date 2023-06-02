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

package com.sadapay.sadaparcel.modules.offer.contract

import lombok.Getter
import lombok.NonNull
import lombok.RequiredArgsConstructor
import lombok.experimental.Accessors
import java.io.Serializable
import javax.validation.constraints.NotNull

/**
 * A DTO for the {@link com.sadapay.sadaparcel.modules.models.entities.Offer} entity
 */
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
data class OfferDto(
    @NotNull
    @NonNull
    val id: String = "",
    @NotNull
    @NonNull
    val itemId: String = "",
    @NotNull
    @NonNull
    val name: String = "",
    @NotNull
    @NonNull
    val description: String = "",
    @NotNull
    @NonNull
    val priceReduction: Double = 0.0,
    @NotNull
    @NonNull
    val quantityThreshold: Int = 0
) : Serializable {
    constructor(offerId: Array<*>) : this(
        offerId[0] as? String ?: "",
        offerId[1] as? String ?: "",
        offerId[2] as? String ?: "",
        offerId[3] as? String ?: "",
        offerId[4] as? Double ?: 0.0,
        offerId[5] as? Int ?: 0
    )

    companion object {
        private const val serialVersionUID = -2231830563027284790L
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OfferDto) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (id != other.id) return false
        if (itemId != other.itemId) return false
        if (priceReduction != other.priceReduction) return false
        if (quantityThreshold != other.quantityThreshold) return false
        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + itemId.hashCode()
        result = 31 * result + priceReduction.hashCode()
        result = 31 * result + quantityThreshold.hashCode()
        return result
    }

    override fun toString(): String {
        return "OfferDTO{" +
                "name='$name', " +
                "description='$description'" +
                "itemId='$itemId', " +
                "priceReduction=$priceReduction, " +
                "quantityThreshold=$quantityThreshold " +
                '}'
    }
}