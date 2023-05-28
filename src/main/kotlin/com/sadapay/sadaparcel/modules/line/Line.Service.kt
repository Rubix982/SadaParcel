/*
 * Copyright 2023, Saif Ul Islam @ SadaParcel, Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the “Software”), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

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
                composer.wrapWithLogs(itemDto, entity.configProcessor),
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