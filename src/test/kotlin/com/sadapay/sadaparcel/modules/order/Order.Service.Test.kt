package com.sadapay.sadaparcel.modules.order

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class OrderServiceTest(@Autowired private val orderService: OrderService) {

    @Test
    fun whenApplicationStarts_ThenHibernateCreatesInitialRecords() {

        // Given a fetched list from the OrderService
        val orders = orderService.list()

        // When Hibernate creates initial records

        // Then the list is empty
        assert(orders?.size == 0)
    }
}