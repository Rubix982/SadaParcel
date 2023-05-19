package com.sadapay.sadaparcel.modules.item

import com.sadapay.sadaparcel.modules.models.entities.Item
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
class ItemsController @Autowired constructor(itemService: ItemService?) {
    private val itemService: ItemService?

    init {
        this.itemService = itemService
    }

    @GetMapping("/items", produces = ["application/json"])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun index(): MutableIterable<Item?>? = itemService?.findAll()
}
