package com.sadapay.sadaparcel.modules.line

import com.sadapay.sadaparcel.modules.item.ItemDto
import com.sadapay.sadaparcel.modules.itemsmanagement.ItemsManagementService
import com.sadapay.sadaparcel.modules.models.entities.EntityWithLogs
import com.sadapay.sadaparcel.modules.models.entities.Item
import com.sadapay.sadaparcel.modules.models.entities.Line
import com.sadapay.sadaparcel.modules.models.repositories.LineRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.Serializable
import com.sadapay.sadaparcel.modules.transformations.TransformationMonadComposer.Companion as composer

@Service
class LineService(
    @Autowired
    val lineRepository: LineRepository,
    @Autowired
    val itemsManagementService: ItemsManagementService
) {
    fun list(): List<Line?>? {
        return lineRepository.findAll()
    }

    fun save(entity: EntityWithLogs<Serializable?>): EntityWithLogs<Serializable?> {
        val linesDto = entity.entity as LinesDto
        for (line in linesDto.lines) {
            val itemDto: ItemDto = line.item
            val savedItem: EntityWithLogs<Serializable?> = composer.runWithLogs(
                composer.wrapWithLogs(itemDto),
                itemsManagementService::save
            )
            entity.addToLogs(savedItem.logs)
            val builtLine = buildLine(savedItem.entity as Item, line.quantity)
            val savedLine = composer.runWithLogs(
                composer.wrapWithLogs(builtLine),
                this::saveLine
            )
            entity.addToLogs(savedLine.logs)
        }
        return entity
    }

    private fun buildLine(item: Item, quantity: Int): Line {
        val line = Line(item, quantity)
        line.id = lineRepository.count() + 1
        return line
    }

    private fun saveLine(entity: EntityWithLogs<Serializable?>): EntityWithLogs<Serializable?> {
        val line = entity.entity as Line
        lineRepository.save(line)
        entity.addToLogs(String.format(LineConstants.LINE_SAVED, line))
        return entity
    }
}