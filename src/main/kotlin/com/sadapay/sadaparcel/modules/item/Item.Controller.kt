package com.sadapay.sadaparcel.modules.item

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ItemsController(
    @Autowired val itemService: ItemService
) {
    @GetMapping("/items", produces = ["application/json"])
    @ResponseBody
    fun index() = itemService.findAll()
}
