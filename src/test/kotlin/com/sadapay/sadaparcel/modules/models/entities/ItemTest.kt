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
internal class ItemTest {

    private val logger: Logger = LoggerFactory.getLogger(
        ItemTest::class.java
    ) as Logger

    object Constants {
        const val TEST_CLASS_NAME = "ItemTest"
    }

    @Test
    fun testEquals() {
        val testName = "testEquals"
        logTestStarted(testName)

        // given
        val firstItem = Item("id", "name", "description", 0.0, 0.0)
        val secondItem = Item("id", "name", "description", 0.0, 0.0)

        // then
        assertThat(firstItem).isEqualTo(secondItem)

        logTestEnded(testName)
    }

    @Test
    fun testHashCode() {
        val testName = "testHashCode"
        logTestStarted(testName)

        // given
        val firstItem = Item("id", "name", "description", 0.0, 0.0)
        val secondItem = Item("id", "name", "description", 0.0, 0.0)

        // then
        assertThat(firstItem.hashCode()).isEqualTo(secondItem.hashCode())

        logTestEnded(testName)
    }

    @Test
    fun testToString() {
        val testName = "testToString"

        logTestStarted(testName)

        // given
        val item = Item("id", "name", "description", 0.0, 0.0)
        val itemAsString =
            "Item(id='id', name='name', description='description', price=0.0, cost=0.0, line=null, orders=null)"

        // then
        assertThat(item.toString()).isEqualTo(itemAsString)

        logTestEnded(testName)
    }

    @Test
    fun testInEquality() {
        val testName = "testInEquality"
        logTestStarted(testName)

        // given
        val firstItem = Item("id1", "name", "description", 0.0, 0.0)
        val secondItem = Item("id2", "name", "description", 0.0, 0.0)

        // then
        assertThat(firstItem).isNotEqualTo(secondItem)

        logTestEnded(testName)
    }

    @Test
    fun testInEqualityHashCode() {
        val testName = "testInEqualityHashCode"
        logTestStarted(testName)

        // given
        val firstItem = Item("id1", "name", "description", 0.0, 0.0)
        val secondItem = Item("id2", "name", "description", 0.0, 0.0)

        // then
        assertThat(firstItem.hashCode()).isNotEqualTo(secondItem.hashCode())

        logTestEnded(testName)
    }

    @Test
    fun testInEqualityToString() {
        val testName = "testInEqualityToString"
        logTestStarted(testName)

        // given
        val firstItem = Item("id1", "name", "description", 0.0, 0.0)
        val secondItem = Item("id2", "name", "description", 0.0, 0.0)

        // then
        assertThat(firstItem.toString()).isNotEqualTo(secondItem.toString())

        logTestEnded(testName)
    }

    private fun logTestStarted(testName: String) {
        logger.info("[${Constants.TEST_CLASS_NAME}::$testName]: Test started")
    }

    private fun logTestEnded(testName: String) {
        logger.info("[${Constants.TEST_CLASS_NAME}::$testName]: Test ended")
    }
}