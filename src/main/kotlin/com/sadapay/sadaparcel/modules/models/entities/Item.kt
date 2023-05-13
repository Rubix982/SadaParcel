package com.sadapay.sadaparcel.modules.models.entities

import javax.persistence.*

@Entity
@Table(name = "items")
class Item(
    @Id @GeneratedValue
    var id: String,
    @Column
    var name: String,
    @Column
    var description: String,
    @Column
    var price: Double,
    @Column
    var cost: Double,
    @OneToOne(mappedBy = "items")
    var line: Line?
) {
    constructor() : this("", "", "", 0.0, 0.0, null)

    override fun equals(other: Any?): Boolean {

        if (other !is Item) {
            return false
        }

        return other.id == this.id && other.name == this.name && other.description == this.description && other.price == this.price && other.cost == this.cost
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + cost.hashCode()
        return result
    }
}