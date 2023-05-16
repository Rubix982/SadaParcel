package com.sadapay.sadaparcel.modules.models.entities

import javax.persistence.*

@Entity
@Table(name = "items")
class Item(
    @Id @GeneratedValue
    var id: Int,
    @Column(nullable = false, unique = true)
    var itemId: String,
    @Column
    var name: String,
    @Column
    var description: String,
    @Column
    var price: Double,
    @Column
    var cost: Double,
    @OneToOne(mappedBy = "items")
    var line: Line?,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "order_id")
    var orders: Order? = null
) {
    constructor() : this(1, "", "", "", 0.0, 0.0, null)

    constructor(
        id: Int,
        itemId: String,
        name: String,
        description: String,
        price: Double,
        cost: Double
    ) : this(id, itemId, name, description, price, cost, null)

    override fun equals(other: Any?): Boolean {

        if (other !is Item) {
            return false
        }

        return (other.id == id
                && other.itemId == itemId
                && other.name == name
                && other.description == description
                && other.price == price
                && other.cost == cost)
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + cost.hashCode()
        return result
    }

    override fun toString(): String {
        return "Item(id='$id', " +
                "itemId='$itemId' " +
                "name='$name', " +
                "description='$description', " +
                "price=$price, " +
                "cost=$cost, " +
                "line=$line, " +
                "orders=$orders)"
    }
}