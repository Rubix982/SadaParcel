package com.sadapay.sadaparcel.modules.itemsmanagement

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ItemServiceTest(@Autowired private val itemManagementService: ItemsManagementService) {

    @Test
    fun whenApplicationStarts_ThenHibernateCreatesInitialRecords() {

        // Given a fetched list from the ItemService
        val items = itemManagementService.list()

        // When Hibernate creates initial records

        // Then the list is empty
        assert(items?.size == 0)
    }
}
