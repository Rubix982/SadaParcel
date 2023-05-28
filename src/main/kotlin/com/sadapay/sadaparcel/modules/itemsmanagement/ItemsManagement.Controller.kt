package com.sadapay.sadaparcel.modules.itemsmanagement

import com.sadapay.sadaparcel.modules.line.LineService
import com.sadapay.sadaparcel.modules.line.LinesDto
import com.sadapay.sadaparcel.modules.models.entities.Item
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import com.sadapay.sadaparcel.config.controller.GlobalControllerConstants as constants
import com.sadapay.sadaparcel.modules.transformations.TransformationMonadComposer.Companion as composer

@RestController
class ItemsManagementController @Autowired constructor(
    itemsManagementService: ItemsManagementService?,
    lineService: LineService?
) {

    private val itemsManagementService: ItemsManagementService?

    private val lineService: LineService?

    init {
        this.itemsManagementService = itemsManagementService
        this.lineService = lineService
    }

    @GetMapping(ItemsManagementConstants.ROUTE, produces = [constants.JSON])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun index(): MutableIterable<Item?>? = itemsManagementService?.findAll()

    @PostMapping(ItemsManagementConstants.ROUTE, produces = [constants.JSON])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun add(@Valid @RequestBody lines: LinesDto): ResponseEntity<LinesDto> {


        if (lines.lines.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(lines)
        }

        lineService?.save(lines)
        return ResponseEntity.status(HttpStatus.CREATED).body(lines)
    }

    @DeleteMapping(ItemsManagementConstants.ROUTE, produces = [constants.JSON])
    @ResponseBody
    fun remove(itemIds: List<String>): ResponseEntity<List<String>> {

        if (itemIds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(listOf())
        }

        itemsManagementService?.areAllIdsValid(itemIds)?.let { areAllIdsValid ->
            if (!areAllIdsValid) {
                // If itemIds has some or all invalid Ids, return a 409 Conflict
                // https://stackoverflow.com/questions/25122472/rest-http-status-code-if-delete-impossible
                return ResponseEntity.status(HttpStatus.CONFLICT).body(listOf())
            }
        }

        itemsManagementService?.deleteItems(itemIds)

        return ResponseEntity.status(HttpStatus.OK).body(itemIds)
    }
}
