package com.sadapay.sadaparcel.modules.item

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ItemServiceTest(@Autowired private val itemService: ItemService) {

    @Test
    fun whenApplicationStarts_ThenHibernateCreatesInitialRecords() {

        // Given a fetched list from the ItemService
        val items = itemService.findAll()

        // When Hibernate creates initial records

        // Then the list is empty
        assert(items.count() == 0)
    }
}
