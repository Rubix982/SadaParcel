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

package com.sadapay.sadaparcel.modules.order.contract

import java.io.Serializable

/**
 * A DTO for the {@link com.sadapay.sadaparcel.modules.models.entities.Item} entity
 */
data class ItemSummary(
    val id: String,
    val originalPrice: Double = 0.0,
    val discountPrice: Double = 0.0,
    val name: String = ""
) : Serializable {
    companion object {
        private const val serialVersionUID = 638186280382266971L
    }

    override fun toString(): String {
        return """ItemSummary{id='$id', name='$name', originalPrice=$originalPrice, discountPrice=$discountPrice}"""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ItemSummary) return false
        if (id != other.id) return false
        if (name != other.name) return false
        if (originalPrice != other.originalPrice) return false
        return discountPrice == other.discountPrice
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + originalPrice.hashCode()
        result = 31 * result + discountPrice.hashCode()
        return result
    }
}