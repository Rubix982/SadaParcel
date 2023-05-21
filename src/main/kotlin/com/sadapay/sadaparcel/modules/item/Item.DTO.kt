package com.sadapay.sadaparcel.modules.item

import lombok.Getter
import lombok.NonNull
import lombok.RequiredArgsConstructor
import lombok.experimental.Accessors
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * A DTO for the {@link com.sadapay.sadaparcel.modules.models.entities.Item} entity
 */
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
data class ItemDto(
    @NotBlank
    @NotNull
    @NonNull
    var id: String = "",
    @NotNull
    @NonNull
    var name: String = "",
    @NotNull
    @NonNull
    var description: String = "",
    @NotNull
    @NonNull
    var price: Double = 0.0,
    @NotNull
    @NonNull
    var cost: Double = 0.0
) : Serializable {
    companion object {
        private const val serialVersionUID = -6773356372607147358L
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ItemDto) return false
        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (price != other.price) return false
        if (cost != other.cost) return false
        return true
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
        return "ItemDTO{" +
                "id='$id', " +
                "name='$name', " +
                "description='$description', " +
                "price=$price, " +
                "cost=$cost" +
                '}'
    }
}