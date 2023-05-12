package com.sadapay.sadaparcel.modules.offer

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class OfferServiceTest(@Autowired private val offerService: OfferService) {

    @Test
    fun whenApplicationStarts_ThenHibernateCreatesInitialRecords() {

        // Given a fetched list from the OfferService
        val offers = offerService.list()

        // When Hibernate creates initial records

        // Then the list is empty
        assert(offers?.size == 0)
    }
}