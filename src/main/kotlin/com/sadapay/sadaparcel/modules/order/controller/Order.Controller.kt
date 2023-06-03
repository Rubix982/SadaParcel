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

package com.sadapay.sadaparcel.modules.order.controller

import com.sadapay.sadaparcel.config.controller.GlobalControllerConstants
import com.sadapay.sadaparcel.modules.models.entities.Order
import com.sadapay.sadaparcel.modules.order.constants.OrderConstants
import com.sadapay.sadaparcel.modules.order.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class OrderController @Autowired constructor(orderService: OrderService?) {

    private val orderService: OrderService?

    init {
        this.orderService = orderService
    }

    @GetMapping(OrderConstants.ROUTE, produces = [GlobalControllerConstants.JSON])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun getOrders(): ResponseEntity<MutableList<Order?>> =
        ResponseEntity.status(HttpStatus.OK).body(orderService?.findAll())

    @PostMapping(OrderConstants.ROUTE, produces = [GlobalControllerConstants.JSON])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun addOrder(@Valid @RequestBody order: Order): ResponseEntity<Order> {
        return ResponseEntity.status(HttpStatus.OK).body(order)
    }
}