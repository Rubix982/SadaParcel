package com.sadapay.sadaparcel.modules.models.entities

class Offer(
    var id: String,
    var name: String,
    var description: String,
    var itemId: String,
    var priceReduction: Double,
    var quantityThreshold: Int
) {
    fun equals(offer: Offer): Boolean {
        return offer.id == id && offer.name == name && offer.description == description && offer.itemId == itemId
                && offer.priceReduction == priceReduction && offer.quantityThreshold == quantityThreshold
    }
}