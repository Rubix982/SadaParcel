package com.sadapay.sadaparcel.modules.models.entities

import com.sadapay.sadaparcel.modules.item.ItemDto
import com.sadapay.sadaparcel.modules.models.constants.ItemConstants
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.io.Serializable
import javax.persistence.*

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = ItemConstants.TABLE_NAME)
class Item(
    @Id
    @Column(nullable = false, unique = true)
    var id: Long,
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
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = ItemConstants.ORDER_JOIN_COLUMN_NAME)
    var orders: Order? = null
) : Serializable {
    constructor() : this(1, "", "", "", 0.0, 0.0, null)

    constructor(
        id: Long,
        itemId: String,
        name: String,
        description: String,
        price: Double,
        cost: Double
    ) : this(id, itemId, name, description, price, cost, null)

    constructor(
        itemId: String,
        name: String,
        description: String,
        price: Double,
        cost: Double
    ) : this(1, itemId, name, description, price, cost, null)

    constructor(
        itemDTO: ItemDto
    ) : this(itemDTO.id, itemDTO.name, itemDTO.description, itemDTO.price, itemDTO.cost)

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
        result = 31 * result + itemId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + cost.hashCode()
        return result
    }

    override fun toString(): String {
        return "Item(id='$id', " +
                "itemId='$itemId', " +
                "name='$name', " +
                "description='$description', " +
                "price=$price, " +
                "cost=$cost, " +
                "orders=$orders)"
    }
}