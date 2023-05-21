package com.sadapay.sadaparcel.modules.line

import com.sadapay.sadaparcel.modules.item.ItemDto
import com.sadapay.sadaparcel.modules.itemsmanagement.ItemsManagementService
import com.sadapay.sadaparcel.modules.models.entities.Item
import com.sadapay.sadaparcel.modules.models.entities.Line
import com.sadapay.sadaparcel.modules.models.repositories.LineRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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

    fun save(lines: LinesDto) {
        for (line in lines.lines) {
            val itemDto: ItemDto = line.item
            itemsManagementService.save(itemDto)
            save(Line(Item(itemDto), line.quantity))
        }
    }

    fun save(line: Line) {
        lineRepository.save(line)
    }
}