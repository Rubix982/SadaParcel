package com.sadapay.sadaparcel.modules.itemsmanagement

import com.sadapay.sadaparcel.modules.item.ItemDto
import com.sadapay.sadaparcel.modules.models.entities.Item
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class ItemsManagementController @Autowired constructor(itemsManagementService: ItemsManagementService) {

    private val itemsManagementService: ItemsManagementService

    init {
        this.itemsManagementService = itemsManagementService
    }

    @GetMapping("/items-management", produces = ["application/json"])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun index(): MutableIterable<Item?> = itemsManagementService.findAll()

    @PostMapping("/items-management", produces = ["application/json"])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun add(@Valid @RequestBody itemDto: ItemDto): ResponseEntity<ItemDto> {

        if (itemDto.itemId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(itemDto)
        }

        itemsManagementService.save(itemDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(itemDto)
    }

    @DeleteMapping(produces = ["application/json"])
    @ResponseBody
    fun remove(itemIds: List<String>): ResponseEntity<List<String>> {

        if (itemIds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(listOf())
        }

        // TODO: If itemIds has some or all invalid Ids, return a 409 Conflict
        // https://stackoverflow.com/questions/25122472/rest-http-status-code-if-delete-impossible

        return ResponseEntity.status(HttpStatus.OK).body(itemIds)
    }
}
