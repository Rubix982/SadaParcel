package com.sadapay.sadaparcel.modules.models.entities

import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.io.Serializable
import javax.persistence.*

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "lines")
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