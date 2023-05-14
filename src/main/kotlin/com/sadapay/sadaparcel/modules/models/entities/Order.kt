package com.sadapay.sadaparcel.modules.models.entities

import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(
    @Id @GeneratedValue
    var id: String,
    @OneToMany(mappedBy = "orders", cascade = [CascadeType.ALL])
    var items: List<Item>,
    @OneToMany(mappedBy = "orders", cascade = [CascadeType.ALL])
    var offers: List<Offer>,
) {
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