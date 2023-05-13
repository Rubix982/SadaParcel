package com.sadapay.sadaparcel.modules.models.entities

import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(
    @Id @GeneratedValue
    var id: String,
    @Column
    var name: String,
    @Column
    var originalPrice: Double,
    @Column
    var discountedPrice: Double
) {
    constructor() : this("", "", 0.0, 0.0)

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