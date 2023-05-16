package com.sadapay.sadaparcel.modules.item

import java.io.Serializable

/**
 * A DTO for the {@link com.sadapay.sadaparcel.modules.models.entities.Item} entity
 */
data class ItemDTO(
    var itemId: String = "",
    var name: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var cost: Double = 0.0
) : Serializable {
    companion object {
        private const val serialVersionUID = -6773356372607147358L
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ItemDTO) return false
        if (itemId != other.itemId) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (price != other.price) return false
        if (cost != other.cost) return false
        return true
    }

    override fun hashCode(): Int {
        var result = itemId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + cost.hashCode()
        return result
    }

    override fun toString(): String {
        return "ItemDTO{" +
                "itemId='" + itemId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", cost=" + cost +
                '}'
    }
}