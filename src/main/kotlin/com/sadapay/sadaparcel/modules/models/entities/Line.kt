package com.sadapay.sadaparcel.modules.models.entities

import com.sadapay.sadaparcel.modules.models.constants.LineConstants
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.io.Serializable
import javax.persistence.*

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = LineConstants.TABLE_NAME)
class Line(
    @Id
    @Column(name = LineConstants.ID_COLUMN_NAME, nullable = false, unique = true)
    var id: Long,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(
        name = LineConstants.ITEM_JOIN_COLUMN_NAME,
        referencedColumnName = LineConstants.ITEM_REFERENCED_COLUMN_NAME
    )
    var items: Item,
    @Column
    var quantity: Int
) : Serializable {
    constructor() : this(1, Item(), 0)

    constructor(item: Item, quantity: Int) : this(1, item, quantity)

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