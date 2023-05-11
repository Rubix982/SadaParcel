package com.sadapay.sadaparcel.modules.models.entities

class Item(
    var id: String,
    var name: String,
    var description: String,
    var price: Double
) {
    fun equals(item: Item): Boolean {
        return item.id == this.id && item.name == this.name && item.description == this.description && item.price == this.price
    }
}