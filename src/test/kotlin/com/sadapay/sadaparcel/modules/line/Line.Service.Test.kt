package com.sadapay.sadaparcel.modules.line

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class LineServiceTest(@Autowired private val lineService: LineService) {

    @Test
    fun list() {
    }

    @Test
    fun getLineRepository() {
    }

    @Test
    fun whenApplicationStarts_ThenHibernateCreatesInitialRecords() {

        // Given a fetched list from the ItemService
        val lines = lineService.list()

        // When Hibernate creates initial records

        // Then the list is empty
        assert(lines?.size == 0)
    }
}