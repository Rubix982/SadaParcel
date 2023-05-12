package com.sadapay.sadaparcel.modules.order

import com.sadapay.sadaparcel.modules.models.entities.Order
import com.sadapay.sadaparcel.modules.models.repositories.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderService(
    @Autowired
    val orderRepository: OrderRepository
) {
    fun list(): List<Order?>? {
        return orderRepository.findAll()
    }
}