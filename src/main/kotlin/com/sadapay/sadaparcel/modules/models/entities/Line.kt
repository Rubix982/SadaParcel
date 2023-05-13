package com.sadapay.sadaparcel.modules.models.entities

import javax.persistence.*

@Entity
class Line(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    var items: Item,
    @Column
    var quantity: Int
) {
    constructor() : this(null, Item(), 0)

    override fun equals(other: Any?): Boolean {

        if (other !is Line) {
            return false
        }

        return other.id == this.id && other.items == this.items && other.quantity == this.quantity
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + items.hashCode()
        result = 31 * result + quantity.hashCode()
        return result
    }
}