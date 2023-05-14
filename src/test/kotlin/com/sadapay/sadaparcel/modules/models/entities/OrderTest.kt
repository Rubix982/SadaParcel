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
internal class OrderTest {

    private val logger: Logger = LoggerFactory.getLogger(
        OrderTest::class.java
    ) as Logger

    object Constants {
        const val TEST_CLASS_NAME = "OrderTest"
    }

    @Test
    fun testEquals() {
        val testName = "testEquals"
        logTestStarted(testName)

        // given
        val firstOrder = Order("id", emptyList(), emptyList())
        val secondOrder = Order("id", emptyList(), emptyList())

        // then
        assertThat(firstOrder).isEqualTo(secondOrder)

        logTestEnded(testName)
    }

    @Test
    fun testHashCode() {
        val testName = "testHashCode"
        logTestStarted(testName)

        // given
        val firstOrder = Order("id", emptyList(), emptyList())
        val secondOrder = Order("id", emptyList(), emptyList())

        // then
        assertThat(firstOrder.hashCode()).isEqualTo(secondOrder.hashCode())

        logTestEnded(testName)
    }

    @Test
    fun testToString() {
        val testName = "testToString"

        logTestStarted(testName)

        // given
        val order = Order("id", emptyList(), emptyList())

        // then
        assertThat(order.toString()).isEqualTo("Order(id='id', items=[], offers=[])")

        logTestEnded(testName)
    }

    @Test
    fun testToStringWithItems() {
        val testName = "testToStringWithItems"

        logTestStarted(testName)

        // given
        val order = Order("id", listOf(Item("id", "name", "description", 0.0, 0.0)), emptyList())
        val orderAsString = "Order(id='id', items=[Item(id='id', name='name', description='description', price=0.0, " +
                "cost=0.0, line=null, orders=null)], offers=[])"

        // then
        assertThat(order.toString()).isEqualTo(orderAsString)

        logTestEnded(testName)
    }

    @Test
    fun testToStringWithOffers() {
        val testName = "testToStringWithOffers"

        logTestStarted(testName)

        // given
        val order = Order("id", emptyList(), listOf(Offer("id", "name", "description", "itemId1", 0.0, 1)))
        val orderAsString = "Order(id='id', items=[], offers=[Offer(id='id', name='name', " +
                "description='description', itemId='itemId1', priceReduction=0.0, quantityThreshold=1)])"

        // then
        assertThat(order.toString()).isEqualTo(orderAsString)

        logTestEnded(testName)
    }

    @Test
    fun testInEquality() {
        val testName = "testInEquality"
        logTestStarted(testName)

        // given
        val firstOrder = Order("id", emptyList(), emptyList())
        val secondOrder = Order("id", emptyList(), listOf(Offer("id", "name", "description", "itemId1", 0.0, 1)))

        // then
        assertThat(firstOrder).isNotEqualTo(secondOrder)

        logTestEnded(testName)
    }

    @Test
    fun testInEqualityWithDifferentId() {
        val testName = "testInEqualityWithDifferentId"
        logTestStarted(testName)

        // given
        val firstOrder = Order("id", emptyList(), emptyList())
        val secondOrder = Order("id2", emptyList(), emptyList())

        // then
        assertThat(firstOrder).isNotEqualTo(secondOrder)

        logTestEnded(testName)
    }

    @Test
    fun testInEqualityWithDifferentItems() {
        val testName = "testInEqualityWithDifferentItems"
        logTestStarted(testName)

        // given
        val firstOrder = Order("id", listOf(Item("id", "name", "description", 0.0, 0.0)), emptyList())
        val secondOrder = Order("id", emptyList(), emptyList())

        // then
        assertThat(firstOrder).isNotEqualTo(secondOrder)

        logTestEnded(testName)
    }

    @Test
    fun testInEqualityWithDifferentOffers() {
        val testName = "testInEqualityWithDifferentOffers"
        logTestStarted(testName)

        // given
        val firstOrder = Order("id", emptyList(), listOf(Offer("id", "name", "description", "itemId1", 0.0, 1)))
        val secondOrder = Order("id", emptyList(), emptyList())

        // then
        assertThat(firstOrder).isNotEqualTo(secondOrder)

        logTestEnded(testName)
    }

    @Test
    fun testInEqualityWithHashCode() {
        val testName = "testInEqualityWithHashCode"
        logTestStarted(testName)

        // given
        val firstOrder = Order("id", emptyList(), emptyList())
        val secondOrder = Order("id", emptyList(), listOf(Offer("id", "name", "description", "itemId1", 0.0, 1)))

        // then
        assertThat(firstOrder.hashCode()).isNotEqualTo(secondOrder.hashCode())

        logTestEnded(testName)
    }

    @Test
    fun testInEqualityToString() {
        val testName = "testInEqualityToString"
        logTestStarted(testName)

        // given
        val firstOrder = Order("id", emptyList(), emptyList())
        val secondOrder = Order("id", emptyList(), listOf(Offer("id", "name", "description", "itemId1", 0.0, 1)))

        // then
        assertThat(firstOrder.toString()).isNotEqualTo(secondOrder.toString())

        logTestEnded(testName)
    }

    private fun logTestStarted(testName: String) {
        logger.info("[${Constants.TEST_CLASS_NAME}::$testName]: Test started")
    }

    private fun logTestEnded(testName: String) {
        logger.info("[${Constants.TEST_CLASS_NAME}::$testName]: Test ended")
    }
}