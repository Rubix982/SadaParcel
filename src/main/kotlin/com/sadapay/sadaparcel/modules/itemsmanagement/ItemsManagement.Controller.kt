package com.sadapay.sadaparcel.modules.itemsmanagement

import com.sadapay.sadaparcel.modules.models.entities.Item
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("items-management")
class ItemsManagementController {
    @GetMapping(produces = ["application/json"])
    @ResponseBody
    fun index(): Collection<Item> = listOf(Item("1", "item1", "ITEM1", 1.0), Item("2", "item2", "ITEM2", 2.0))

    @PostMapping(produces = ["application/json"])
    @ResponseBody
    // TODO: Add @RequestBody annotation
    fun add(item: Item): Item = item

    @DeleteMapping(produces = ["application/json"])
    @ResponseBody
    fun remove(item: Item): Item = item
}
