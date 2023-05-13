package com.sadapay.sadaparcel.modules.itemsmanagement

import com.sadapay.sadaparcel.modules.models.entities.Item
import com.sadapay.sadaparcel.modules.models.entities.Line
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("items-management")
class ItemsManagementController {

    @GetMapping("/", produces = ["application/json"])
    @RequestMapping(value = ["/"], method = [RequestMethod.GET])
    @ResponseBody
    fun index(): Collection<Item> = listOf()

    /*
        @PostMapping("/", produces = ["application/json"])
        @ResponseBody
        fun add(@RequestBody lines: List<Line>): ResponseEntity<List<Line>> {

            if (lines.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(listOf())
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(lines)
        }
    */

    @PostMapping("/", produces = ["application/json"])
    @RequestMapping(value = ["/"], method = [RequestMethod.POST])
    @ResponseBody
    fun add(@RequestBody json: Map<String, Any>): ResponseEntity<List<Line>> {

        val lines =
            json["lines"] as? List<LinkedHashMap<String, Any>> ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(listOf())

        // Convert the LinkedHashMap to Line
        val parsedLines = lines.map {
            val item =
                it["item"] as? Map<String, Any> ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf())
            val id = item["id"] as? String ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf())
            val name = item["name"] as? String ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf())
            val description =
                item["description"] as? String ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf())
            val price = item["price"] as? Double ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf())
            val cost = item["cost"] as? Double ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf())
            val quantity = it["quantity"] as? Int ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf())
            Line(null, Item(id, name, description, price, cost, null, null), quantity)
        }

        if (parsedLines.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(listOf())
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(parsedLines)
    }

    @DeleteMapping(produces = ["application/json"])
    @ResponseBody
    fun remove(item: Item): Item = item
}
