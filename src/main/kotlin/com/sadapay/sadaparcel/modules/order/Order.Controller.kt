package com.sadapay.sadaparcel.modules.order

import com.sadapay.sadaparcel.modules.models.entities.Order
import org.springframework.web.bind.annotation.*

@RequestMapping("orders")
@RestController
class OrderController {

    @GetMapping(produces = ["application/json"])
    @ResponseBody
    fun getOrders(): List<Order> = listOf()

    @PostMapping(produces = ["application/json"])
    @ResponseBody
    fun addOrder(order: Order) {
        TODO()
    }
}