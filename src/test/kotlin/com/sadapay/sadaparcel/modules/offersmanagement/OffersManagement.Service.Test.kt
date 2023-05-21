package com.sadapay.sadaparcel.modules.offersmanagement

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class OffersManagementServiceTest(@Autowired private val offersManagementService: OffersManagementService) {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun list() {
    }

    @Test
    fun getOfferRepository() {
    }

    @Test
    fun whenApplicationStarts_ThenHibernateCreatesInitialRecords() {

        // Given a fetched list from the ItemService
        val items = offersManagementService.findAll()

        // When Hibernate creates initial records

        // Then the list is empty
        assert(items.count() == 0)
    }
}