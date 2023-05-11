package com.sadapay.sadaparcel.modules.item

import com.sadapay.sadaparcel.modules.models.entities.Item
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("items")
class ItemsController {
    @GetMapping("/", produces = ["application/json"])
    @ResponseBody
    fun index() = Item("1", "item1", "ITEM1", 1.0)
}
