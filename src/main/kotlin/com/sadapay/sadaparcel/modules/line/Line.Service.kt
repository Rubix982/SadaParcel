package com.sadapay.sadaparcel.modules.line

import com.sadapay.sadaparcel.modules.models.entities.Line
import com.sadapay.sadaparcel.modules.models.repositories.LineRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LineService(
    @Autowired
    val lineRepository: LineRepository
) {
    fun list(): List<Line?>? {
        return lineRepository.findAll()
    }
}