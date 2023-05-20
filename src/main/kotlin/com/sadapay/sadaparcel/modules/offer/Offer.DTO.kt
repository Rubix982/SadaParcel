package com.sadapay.sadaparcel.modules.offer

import java.io.Serializable
import javax.validation.constraints.NotNull

/**
 * A DTO for the {@link com.sadapay.sadaparcel.modules.models.entities.Offer} entity
 */
data class OfferDto(
    @NotNull
    val offerId: String = "",
    @NotNull
    val itemId: String = "",
    val name: String = "",
    val description: String = "",
    val priceReduction: Double = 0.0,
    val quantityThreshold: Int = 0
) : Serializable {
    companion object {
        private const val serialVersionUID = -2231830563027284790L
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OfferDto) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (offerId != other.offerId) return false
        if (itemId != other.itemId) return false
        if (priceReduction != other.priceReduction) return false
        if (quantityThreshold != other.quantityThreshold) return false
        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + offerId.hashCode()
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