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

import com.sadapay.sadaparcel.modules.models.constants.OrderConstants
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.io.Serializable
import javax.persistence.*

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = OrderConstants.TABLE_NAME)
class Order(
    @Id @GeneratedValue
    var id: String,
    @OneToMany(mappedBy = OrderConstants.ITEMS_MAPPED_BY_COLUMN, cascade = [CascadeType.ALL])
    var items: List<Item>,
    @OneToMany(mappedBy = OrderConstants.OFFERS_MAPPED_BY_COLUMN, cascade = [CascadeType.ALL])
    var offers: List<Offer>,
) : Serializable {
    constructor() : this("", emptyList(), emptyList())

    override fun equals(other: Any?): Boolean {

        if (other !is Order) {
            return false
        }

        return other.id == id && other.items == items && other.offers == offers
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + items.hashCode()
        result = 31 * result + offers.hashCode()
        return result
    }

    override fun toString(): String {
        return "Order(id='$id', items=$items, offers=$offers)"
    }
}