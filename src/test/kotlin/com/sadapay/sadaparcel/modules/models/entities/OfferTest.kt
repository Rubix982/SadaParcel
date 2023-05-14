package com.sadapay.sadaparcel.modules.models.entities

import lombok.extern.log4j.Log4j2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Log4j2
@RunWith(MockitoJUnitRunner::class)
@ExtendWith(MockitoExtension::class)
internal class OfferTest {

    private val logger: Logger = LoggerFactory.getLogger(
        OfferTest::class.java
    ) as Logger

    object Constants {
        const val TEST_CLASS_NAME = "OfferTest"
    }

    @Test
    fun testEquals() {
        val testName = "testEquals"
        logTestStarted(testName)

        // given
        val firstOffer = Offer("id", "name", "description", "itemId1", 0.0, 1)
        val secondOffer = Offer("id", "name", "description", "itemId1", 0.0, 1)

        // then
        assertThat(firstOffer).isEqualTo(secondOffer)

        logTestEnded(testName)
    }

    @Test
    fun testHashCode() {
        val testName = "testHashCode"
        logTestStarted(testName)

        // given
        val firstOffer = Offer("id", "name", "description", "itemId1", 0.0, 1)
        val secondOffer = Offer("id", "name", "description", "itemId1", 0.0, 1)

        // then
        assertThat(firstOffer.hashCode()).isEqualTo(secondOffer.hashCode())

        logTestEnded(testName)
    }

    @Test
    fun testToString() {
        val testName = "testToString"
        logTestStarted(testName)

        // given
        val offer = Offer("id", "name", "description", "itemId1", 0.0, 1)
        val offerAsString =
            "Offer(id='id', name='name', description='description', itemId='itemId1', priceReduction=0.0, quantityThreshold=1)"

        // then
        assertThat(offer.toString()).isEqualTo(offerAsString)

        logTestEnded(testName)
    }

    @Test
    fun testInEquality() {
        val testName = "testInEquality"
        logTestStarted(testName)

        // given
        val firstOffer = Offer("id", "name", "description", "itemId1", 0.0, 1)
        val secondOffer = Offer("id", "name", "description", "itemId2", 0.0, 1)

        // then
        assertThat(firstOffer).isNotEqualTo(secondOffer)

        logTestEnded(testName)
    }

    @Test
    fun testInEqualityWithNull() {
        val testName = "testInEqualityWithNull"
        logTestStarted(testName)

        // given
        val firstOffer = Offer("id", "name", "description", "itemId1", 0.0, 1)
        val secondOffer = null

        // then
        assertThat(firstOffer).isNotEqualTo(secondOffer)

        logTestEnded(testName)
    }

    @Test
    fun testInEqualityWithHashCode() {
        val testName = "testInEqualityWithHashCode"
        logTestStarted(testName)

        // given
        val firstOffer = Offer("id", "name", "description", "itemId1", 0.0, 1)
        val secondOffer = Offer("id", "name", "description", "itemId2", 0.0, 1)

        // then
        assertThat(firstOffer.hashCode()).isNotEqualTo(secondOffer.hashCode())

        logTestEnded(testName)
    }

    @Test
    fun testInEqualityToString() {
        val testName = "testInEqualityToString"
        logTestStarted(testName)

        // given
        val firstOffer = Offer("id", "name", "description", "itemId1", 0.0, 1)
        val secondOffer = Offer("id", "name", "description", "itemId2", 0.0, 1)

        // then
        assertThat(firstOffer.toString()).isNotEqualTo(secondOffer.toString())

        logTestEnded(testName)
    }

    @Test
    fun testEqualityToString() {
        val testName = "testEqualityToString"
        logTestStarted(testName)

        // given
        val firstOffer = Offer("id", "name", "description", "itemId1", 0.0, 1)
        val secondOffer = Offer("id", "name", "description", "itemId1", 0.0, 1)

        // then
        assertThat(firstOffer.toString()).isEqualTo(secondOffer.toString())

        logTestEnded(testName)
    }

    private fun logTestStarted(testName: String) {
        logger.info("[${Constants.TEST_CLASS_NAME}::$testName]: Test started")
    }

    private fun logTestEnded(testName: String) {
        logger.info("[${Constants.TEST_CLASS_NAME}::$testName]: Test ended")
    }
}