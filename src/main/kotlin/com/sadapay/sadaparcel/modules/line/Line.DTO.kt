package com.sadapay.sadaparcel.modules.line

import com.sadapay.sadaparcel.modules.item.ItemDto
import lombok.Getter
import lombok.NonNull
import lombok.RequiredArgsConstructor
import lombok.experimental.Accessors
import java.io.Serializable
import javax.validation.constraints.NotNull

/**
 * A DTO for the {@link com.sadapay.sadaparcel.modules.models.entities.Line} entity
 */
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
data class LinesDto(
    @NotNull
    @NonNull
    var lines: ArrayList<LineDto> = ArrayList()
) : Serializable {
    companion object {
        private const val serialVersionUID = -6773356372607147359L
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LinesDto) return false
        if (lines != other.lines) return false
        return true
    }

    override fun hashCode(): Int {
        return lines.hashCode()
    }

    override fun toString(): String {
        return "LinesDTO{" +
                "lines='$lines', " +
                "}"
    }
}

/**
 * A DTO for the {@link com.sadapay.sadaparcel.modules.models.entities.Line} entity
 */
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
data class LineDto(
    @NotNull
    @NonNull
    val itemDto: ItemDto = ItemDto(),
    @NotNull
    @NonNull
    val quantity: Int = 0
) : Serializable {
    companion object {
        private const val serialVersionUID = -1937216272139075293L
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LineDto) return false
        if (itemDto != other.itemDto) return false
        if (quantity != other.quantity) return false
        return true
    }

    override fun hashCode(): Int {
        var result = itemDto.hashCode()
        result = 31 * result + quantity.hashCode()
        return result
    }

    override fun toString(): String {
        return "LineDTO{" +
                "itemDto='$itemDto', " +
                "quantity='$quantity', " +
                "}"
    }
}