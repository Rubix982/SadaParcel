package com.sadapay.sadaparcel.modules.models.entities

class Order(
    var id: String,
    var name: String,
    var originalPrice: Double,
    var discountedPrice: Double
) {
    override fun equals(other: Any?): Boolean {

        if (other !is Order) {
            return false
        }

        return other.id == id && other.name == name && other.originalPrice == originalPrice && other.discountedPrice == discountedPrice
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + originalPrice.hashCode()
        result = 31 * result + discountedPrice.hashCode()
        return result
    }
}