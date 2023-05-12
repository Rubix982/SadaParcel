package com.sadapay.sadaparcel.modules.models.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Offer(
    @Id @GeneratedValue
    var id: String,
    @Column
    var name: String,
    @Column
    var description: String,
    @Column
    var itemId: String,
    @Column
    var priceReduction: Double,
    @Column
    var quantityThreshold: Int
) {
    constructor() : this("", "", "", "", 0.0, 0)

    override fun equals(other: Any?): Boolean {

        if (other !is Offer) {
            return false
        }

        return other.id == id && other.name == name && other.description == description && other.itemId == itemId
                && other.priceReduction == priceReduction && other.quantityThreshold == quantityThreshold
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + itemId.hashCode()
        result = 31 * result + priceReduction.hashCode()
        result = 31 * result + quantityThreshold
        return result
    }
}