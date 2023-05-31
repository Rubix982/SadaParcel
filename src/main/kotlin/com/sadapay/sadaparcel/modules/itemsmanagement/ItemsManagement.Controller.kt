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

package com.sadapay.sadaparcel.modules.itemsmanagement

import com.sadapay.sadaparcel.modules.item.ItemIdsDto
import com.sadapay.sadaparcel.modules.item.contract.ItemsDto
import com.sadapay.sadaparcel.modules.line.LineService
import com.sadapay.sadaparcel.modules.line.LinesDto
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
    fun index(): ResponseEntity<ItemsDto?> =
        ResponseEntity.status(HttpStatus.OK).body(itemsManagementService?.findAll())

    @PostMapping(ItemsManagementConstants.ROUTE, produces = [constants.JSON])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun add(@Valid @RequestBody lines: LinesDto): ResponseEntity<LinesDto> {

        if (lineService == null) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(lines)
        }

        if (lines.lines.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(lines)
        }

        val entity = composer.wrapWithLogs(lines, ItemsManagementMonadProcessor())
        val outputEntity = composer.runWithLogs(entity, lineService::save)
        composer.writeToLogger(outputEntity.logs)
        val processor = outputEntity.configProcessor as ItemsManagementMonadProcessor

        if (processor.wasAnItemUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(lines)
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(lines)
    }

    @DeleteMapping(ItemsManagementConstants.ROUTE, produces = [constants.JSON])
    @ResponseBody
    fun remove(@Valid @RequestBody itemIdsDto: ItemIdsDto): ResponseEntity<ItemIdsDto> {

        val itemIds = itemIdsDto.itemIds

        if (itemsManagementService == null) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ItemIdsDto())
        }

        if (itemIds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ItemIdsDto())
        }

        itemsManagementService.areAllIdsValid(itemIds).let { areAllIdsValid ->
            if (!areAllIdsValid) {
                // If itemIds has some or all invalid Ids, return a 409 Conflict
                // https://stackoverflow.com/questions/25122472/rest-http-status-code-if-delete-impossible
                return ResponseEntity.status(HttpStatus.CONFLICT).body(ItemIdsDto())
            }
        }

        val entity = composer.wrapWithLogs(itemIdsDto, ItemsManagementMonadProcessor())
        val outputEntity = composer.runWithLogs(entity, itemsManagementService::deleteItems)
        composer.writeToLogger(outputEntity.logs)

        return ResponseEntity.status(HttpStatus.OK).body(ItemIdsDto(itemIds))
    }
}
