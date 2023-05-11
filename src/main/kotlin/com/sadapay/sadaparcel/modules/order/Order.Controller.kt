package com.sadapay.sadaparcel.modules.order

import com.sadapay.sadaparcel.modules.models.entities.Order
import org.springframework.web.bind.annotation.*

@RequestMapping("orders")
@RestController
class OrdersController {

    @GetMapping(produces = ["application/json"])
    @ResponseBody
    fun getOrders(): List<Order> = listOf(Order("1", "2", 1.2, 2.3), Order("4", "5", 1.2, 2.3))

    @PostMapping(produces = ["application/json"])
    @ResponseBody
    fun addOrder(order: Order) {
        TODO()
    }
}