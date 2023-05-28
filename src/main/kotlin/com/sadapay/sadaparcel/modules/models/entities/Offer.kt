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

package com.sadapay.sadaparcel.modules.models.entities

import com.sadapay.sadaparcel.modules.models.constants.OfferConstants
import com.sadapay.sadaparcel.modules.offer.OfferDto
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.io.Serializable
import javax.persistence.*

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = OfferConstants.TABLE_NAME)
class Offer(
    @Id @GeneratedValue
    var id: Int,
    @Column
    var offerId: String,
    @Column
    var itemId: String,
    @Column
    var name: String,
    @Column
    var description: String,
    @Column
    var priceReduction: Double,
    @Column
    var quantityThreshold: Int,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = OfferConstants.ORDER_REFERENCED_COLUMN_NAME)
    var orders: Order? = null
) : Serializable {
    constructor() : this(1, "", "", "", "", 0.0, 0)

    constructor(
        offerDto: OfferDto
    ) : this(
        1,
        offerDto.offerId,
        offerDto.itemId,
        offerDto.name,
        offerDto.description,
        offerDto.priceReduction,
        offerDto.quantityThreshold
    )

    constructor(
        id: Int,
        offerId: String,
        itemId: String,
        name: String,
        description: String,
        priceReduction: Double,
        quantityThreshold: Int
    ) : this(id, offerId, itemId, name, description, priceReduction, quantityThreshold, null)

    constructor(
        offerId: String,
        itemId: String,
        name: String,
        description: String,
        priceReduction: Double,
        quantityThreshold: Int
    ) : this(1, offerId, itemId, name, description, priceReduction, quantityThreshold, null)

    override fun equals(other: Any?): Boolean {

        if (other !is Offer) {
            return false
        }

        return other.id == id && other.offerId == offerId && other.itemId == itemId && other.name == name
                && other.description == description && other.priceReduction == priceReduction
                && other.quantityThreshold == quantityThreshold
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + offerId.hashCode()
        result = 31 * result + itemId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + priceReduction.hashCode()
        result = 31 * result + quantityThreshold
        return result
    }

    override fun toString(): String {
        return "Offer(id='$id', " +
                "itemId='$itemId', " +
                "offerId='$offerId', " +
                "name='$name', " +
                "description='$description', " +
                "priceReduction=$priceReduction, " +
                "quantityThreshold=$quantityThreshold)"
    }
}