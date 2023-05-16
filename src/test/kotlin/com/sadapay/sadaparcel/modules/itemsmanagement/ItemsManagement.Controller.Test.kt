package com.sadapay.sadaparcel.modules.itemsmanagement

import com.fasterxml.jackson.databind.ObjectMapper
import com.sadapay.sadaparcel.modules.models.entities.Item
import com.sadapay.sadaparcel.modules.models.entities.Line
import com.sadapay.sadaparcel.modules.models.repositories.ItemRepository
import com.sadapay.sadaparcel.server.rules.SadaParcelHttpHeaderFilter
import lombok.extern.log4j.Log4j2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.json.JacksonTester
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder
import kotlin.random.Random


@Log4j2
@RunWith(MockitoJUnitRunner::class)
@ExtendWith(MockitoExtension::class)
class ItemsManagementControllerTest {

    private val logger: Logger = LoggerFactory.getLogger(
        ItemsManagementControllerTest::class.java
    ) as Logger

    private var mvc: MockMvc? = null

    @InjectMocks
    private val itemsManagementController: ItemsManagementController? = null

    @Mock
    private val itemRepository: ItemRepository? = null

    object ControllerConstants {
        const val CONTROLLER_ROUTE = "/items-management"
        const val TEST_CLASS_NAME = "ItemsManagementControllerTest"
    }

    private val items: List<Item> = List(3) {
        Item(
            itemId = "minim aliqua",
            name = "Excepteur sunt labore dolor",
            description = "sint velit deserunt laborum",
            price = -54939028.11428884,
            cost = -31369367.44365509
        )
        Item(
            itemId = "consectetur deserunt ipsum lab",
            name = "veniam nulla labore eiusmod",
            description = "ea amet sint dolor",
            price = -5493538.6548884,
            cost = -313267.4547509
        )
        Item(
            itemId = "Nemo enim ipsam",
            name = "voluptatem quia voluptas sit aspernatur",
            description = "aut odit aut fugit, sed quia consequuntur magni dolores",
            price = -5734528.115454,
            cost = -3133347.464509
        )
    }

    @BeforeEach
    fun setUp() {
        logger.info("[${ControllerConstants.TEST_CLASS_NAME}::setUp]: Setup complete")

        JacksonTester.initFields(this, ObjectMapper())
        mvc = MockMvcBuilders.standaloneSetup(itemsManagementController)
            .setControllerAdvice(ItemsManagementExceptionHandler())
            .addFilters<StandaloneMockMvcBuilder>(SadaParcelHttpHeaderFilter())
            .build()
        logger.info("[${ControllerConstants.TEST_CLASS_NAME}::setUp::MockMvc::Successful]: mvc: $mvc")
    }

    @AfterEach
    fun tearDown() {
        logger.info("[${ControllerConstants.TEST_CLASS_NAME}::tearDown]: Tear down complete")
    }

    @Test
    fun index() {
        logger.info("[${ControllerConstants.TEST_CLASS_NAME}::index]: ${ControllerConstants.TEST_CLASS_NAME} detected")
    }

    @Test
    fun postWhenNoItemIsAddedThenHttpStatusShouldBeNoContent() {
        val testName = "postWhenNoItemIsAddedThenHttpStatusShouldBeNoContent"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        given(itemRepository.findAll()).willReturn(emptyList())

        // when
        val mockedHttpServletResponse = mockHttpPOSTServletResponse(emptyList()) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.status).isEqualTo(204)

        logTestEnded(testName)
    }

    @Test
    fun postWhenNoItemIsAddedThenNoItemIsReturned() {
        val testName = "postWhenNoItemIsAddedThenNoItemIsReturned"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // when
        val mockedHttpServletResponse = mockHttpPOSTServletResponse(emptyList()) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.contentLength).isEqualTo(0)

        logTestEnded(testName)
    }

    @Test
    fun postWhenOneItemIsAddedAndRepositoryIsEmptyThenHttpStatusShouldBeCreated() {
        val testName = "postWhenOneItemIsAddedThenHttpStatusShouldBeCreated"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        itemRepository.deleteAll()
        given(itemRepository.findAll()).willReturn(emptyList())

        // when
        val line = List(1) { Line(1, items[0], 10) }
        val mockedHttpServletResponse = mockHttpPOSTServletResponse(line) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.status).isEqualTo(201)

        logTestEnded(testName)
    }

    @Test
    fun postWhenOneItemIsAddedAndRepositoryIsEmptyThenRepositoryShouldPersistOneItem() {
        val testName = "postWhenOneItemIsAddedAndRepositoryIsEmptyThenRepositoryShouldPersistNewItem"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        itemRepository.deleteAll()
        given(itemRepository.findAll()).willReturn(emptyList())

        // when
        val line = List(1) { Line(1, items[0], 10) }
        val mockedHttpServletResponse = mockHttpPOSTServletResponse(line) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(itemRepository.findAll().count()).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenOneItemIsAddedAndRepositoryIsEmptyThenRepositoryShouldPersistDataAsExpected() {
        val testName = "postWhenOneItemIsAddedAndRepositoryIsEmptyThenRepositoryShouldPersistDataAsExpected"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        itemRepository.deleteAll()
        given(itemRepository.findAll()).willReturn(emptyList())

        // when
        val line = List(1) { Line(1, items[0], 10) }
        val mockedHttpServletResponse = mockHttpPOSTServletResponse(line) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        val itemsFromRepository = itemRepository.findAll()
        val item = itemsFromRepository.elementAt(0)
        assertThat(item == items[0]).isEqualTo(true)

        logTestEnded(testName)
    }

    @Test
    fun postWhenRepositoryIsNotEmptyAndItemIsNotPreviouslyAddedThenHttpStatusShouldBeCreated() {
        val testName = "postWhenOneItemIsAddedThenHttpStatusShouldBeCreated"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        itemRepository.deleteAll()
        itemRepository.save(items[0])
        given(itemRepository.findAll().count()).willReturn(1)

        // when
        val line = List(1) { Line(1, items[1], 10) }
        val mockedHttpServletResponse = mockHttpPOSTServletResponse(line) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.status).isEqualTo(201)

        logTestEnded(testName)
    }

    @Test
    fun postWhenRepositoryIsNotEmptyAndItemExistsPreviouslyThenHttpStatusBeShouldOK() {
        val testName = "postWhenOneItemIsAddedThenHttpStatusShouldBeCreated"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        itemRepository.deleteAll()
        itemRepository.save(items[0])
        given(itemRepository.findAll().count()).willReturn(1)

        // when
        val line = List(1) { Line(1, items[0], 10) }
        val mockedHttpServletResponse = mockHttpPOSTServletResponse(line) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.status).isEqualTo(200)

        logTestEnded(testName)
    }

    @Test
    fun postWhenRepositoryIsNotEmptyAndItemExistsPreviouslyThenRepositoryIsUpdated() {
        val testName = "postWhenOneItemIsAddedThenHttpStatusShouldBeCreated"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        itemRepository.deleteAll()
        itemRepository.save(items[0])
        given(itemRepository.findAll().count()).willReturn(1)

        // when
        val line = List(1) { Line(1, items[0], 10) }
        line[0].items.name = "new name"
        val mockedHttpServletResponse = mockHttpPOSTServletResponse(line) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(itemRepository.findAll().elementAt(0)?.name).isEqualTo("new name")

        logTestEnded(testName)
    }

    @Test
    fun postWhenAnyLinesArePassedThenThoseLinesAreReturnedExactly() {
        val testName = "postWhenAnyLinesArePassedThenThoseLinesAreReturnedExactly"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // when
        val lines = List(3) {
            Line(1, items[0], 10)
            Line(2, items[1], 20)
            Line(3, items[2], 30)
        }
        val mockedHttpServletResponse = mockHttpPOSTServletResponse(lines) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.contentLength).isEqualTo(lines.size)

        logTestEnded(testName)
    }

    @Test
    fun getWhenRepositoryIsEmptyThenReturnNoItems() {
        val testName = "getWhenRepositoryIsEmptyThenReturnNoItems"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        itemRepository.deleteAll()
        val totalItems = itemRepository.findAll().count()
        given(totalItems).willReturn(0)

        // when
        val mockedHttpServletResponse = mockHttpGETServletResponse() ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.contentLength).isEqualTo(totalItems)

        logTestEnded(testName)
    }

    @Test
    fun getWhenRepositoryIsEmptyThenReturnHttpStatusOK() {
        val testName = "getWhenRepositoryIsEmptyThenReturnHttpStatusOK"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        itemRepository.deleteAll()
        val totalItems = itemRepository.findAll().count()
        given(totalItems).willReturn(0)

        // when
        val mockedHttpServletResponse = mockHttpGETServletResponse() ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.status).isEqualTo(HttpStatus.OK.value())

        logTestEnded(testName)
    }

    @Test
    fun getWhenRepositoryHasOneElementThenReturnOneItem() {
        val testName = "getWhenRepositoryHasOneElementThenReturnOneItem"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        itemRepository.deleteAll()
        itemRepository.save(items[0])
        val totalItems = itemRepository.findAll().count()
        given(totalItems).willReturn(1)

        // when
        val mockedHttpServletResponse = mockHttpGETServletResponse() ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.contentLength).isEqualTo(totalItems)

        logTestEnded(testName)
    }

    @Test
    fun getWhenRepositoryHasOneElementThenReturnHttpStatusOK() {
        val testName = "getWhenRepositoryHasOneElementThenReturnHttpStatusOK"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        itemRepository.deleteAll()
        itemRepository.save(items[0])
        val totalItems = itemRepository.findAll().count()
        given(totalItems).willReturn(1)

        // when
        val mockedHttpServletResponse = mockHttpGETServletResponse() ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.status).isEqualTo(HttpStatus.OK.value())

        logTestEnded(testName)
    }

    @Test
    fun getWhenRepositoryHasOneItemAddedTwiceAddedThenReturnOnlyOneItem() {
        val testName = "getWhenRepositoryHasOneItemAddedTwiceAddedThenReturnOnlyOneItem"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        itemRepository.deleteAll()
        itemRepository.save(items[0])
        itemRepository.save(items[0])
        val totalItems = itemRepository.findAll().count()
        given(totalItems).willReturn(2)

        // when
        val mockedHttpServletResponse = mockHttpGETServletResponse() ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.status).isEqualTo(HttpStatus.OK.value())

        logTestStarted(testName)
    }

    @Test
    @Throws(Exception::class)
    fun getWhenHeaderIsPresentThenHttpStatusIsOK() {
        val testName = "getWhenHeaderIsPresentThenHttpStatusIsOK"
        logTestStarted(testName)

        // when
        val response: MockHttpServletResponse? = mockHttpGETServletResponse()

        // then
        assertThat(response?.status).isEqualTo(HttpStatus.OK.value())

        logTestEnded(testName)
    }

    @Test
    @Throws(Exception::class)
    fun getWhenHeaderIsPresentThenHeaderContainsAPP() {
        val testName = "getWhenHeaderIsPresentThenHeaderContainsAPP"
        logTestStarted(testName)

        // when
        val response: MockHttpServletResponse? = mockHttpGETServletResponse()

        // then
        assertThat(response?.getHeaders("X-SADAPARCEL-APP")).containsOnly("sadaparcel-header")

        logTestEnded(testName)
    }

    @Test
    fun deleteWhenNoItemIdsArePassedThenRepositoryIsUnaffected() {
        val testName = "deleteWhenNoItemIdsArePassedThenRepositoryIsUnaffected"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        val itemCount = itemRepository.findAll().count()

        // when
        val mockedHttpServletResponse = mockHttpDELETEResponse(emptyList()) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(itemRepository.findAll().count()).isEqualTo(itemCount)

        logTestEnded(testName)
    }

    @Test
    fun deleteWhenNoItemIdsArePassedThenHttpStatusIsNoContent() {
        val testName = "deleteWhenNoItemIdsArePassedThenHttpStatusIsNoContent"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // when
        val mockedHttpServletResponse = mockHttpDELETEResponse(emptyList()) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.status).isEqualTo(HttpStatus.NO_CONTENT.value())

        logTestEnded(testName)
    }

    @Test
    fun deleteWhenAllItemIdsAreValidThenRepositoryIsAffected() {
        val testName = "deleteWhenAllItemIdsAreValidThenRepositoryIsAffected"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        itemRepository.saveAll(items)

        // when
        val itemIds: MutableList<String> = mutableListOf()
        items.forEach { item -> itemIds.add(item.itemId) }
        val mockedHttpServletResponse = mockHttpDELETEResponse(itemIds) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(itemRepository.findAll().count()).isEqualTo(0)

        logTestEnded(testName)
    }

    @Test
    fun deleteWhenAllItemIdsAreValidThenHttpStatusIsNoContent() {
        val testName = "deleteWhenAllItemIdsAreValidThenHttpStatusIsNoContent"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        itemRepository.saveAll(items)

        // when
        val itemIds: MutableList<String> = mutableListOf()
        items.forEach { item -> itemIds.add(item.itemId) }
        val mockedHttpServletResponse = mockHttpDELETEResponse(itemIds) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.status).isEqualTo(HttpStatus.NO_CONTENT.value())

        logTestEnded(testName)
    }

    @Test
    fun deleteWhenAllItemIdsAreValidThenReturnedIsTheSameListOfItemIds() {
        val testName = "deleteWhenAllItemIdsAreValidThenReturnedIsTheSameListOfItemIds"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        itemRepository.saveAll(items)

        // when
        val itemIds: MutableList<String> = mutableListOf()
        items.forEach { item -> itemIds.add(item.itemId) }
        val mockedHttpServletResponse = mockHttpDELETEResponse(itemIds) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.contentAsString).isEqualTo(itemIds.toString())

        logTestEnded(testName)
    }

    @Test
    fun deleteWhenSomeItemIdsAreInvalidThenHttpStatusIsConflict() {
        val testName = "deleteWhenSomeItemIdsAreInvalidThenHttpStatusIsConflict"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        itemRepository.saveAll(items)

        // when
        val itemIds: MutableList<String> = mutableListOf()
        items.forEachIndexed { index, item ->
            run {
                var itemId: String = item.itemId
                if (index < 3) itemId = jumbleWord(itemId)
                itemIds.add(itemId)
            }
        }
        val mockedHttpServletResponse = mockHttpDELETEResponse(itemIds) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.status).isEqualTo(HttpStatus.CONFLICT.value())

        logTestEnded(testName)
    }

    @Test
    fun deleteWhenSomeItemIdsAreInvalidThenReturnedIsAnEmptyList() {
        val testName = "deleteWhenSomeItemIdsAreInvalidThenReturnedIsAnEmptyList"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        itemRepository.saveAll(items)

        // when
        val itemIds: MutableList<String> = mutableListOf()
        items.forEachIndexed { index, item ->
            run {
                var itemId: String = item.itemId
                if (index < 3) itemId = jumbleWord(itemId)
                itemIds.add(itemId)
            }
        }
        val mockedHttpServiceResponse = mockHttpDELETEResponse(itemIds) ?: return
        logMockedHttpResponse(testName, mockedHttpServiceResponse)

        // then
        assertThat(mockedHttpServiceResponse.contentAsString).isEqualTo(emptyList<String>().toString())

        logTestEnded(testName)
    }

    @Test
    fun deleteWhenAllItemIdsAreInvalidThenHttpStatusIsConflict() {
        val testName = "deleteWhenSomeItemIdsAreInvalidThenHttpStatusIsConflict"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        itemRepository.saveAll(items)

        // when
        val itemIds: MutableList<String> = mutableListOf()
        items.forEach { item -> itemIds.add(jumbleWord(item.itemId)) }
        val mockedHttpServletResponse = mockHttpDELETEResponse(itemIds) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.status).isEqualTo(HttpStatus.CONFLICT.value())

        logTestEnded(testName)
    }

    @Test
    fun deleteWhenAllItemIdsAreInvalidThenReturnedIsAnEmptyList() {
        val testName = "deleteWhenSomeItemIdsAreInvalidThenReturnedIsAnEmptyList"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        itemRepository.saveAll(items)

        // when
        val itemIds: MutableList<String> = mutableListOf()
        items.forEach { item -> itemIds.add(jumbleWord(item.itemId)) }
        val mockedHttpServiceResponse = mockHttpDELETEResponse(itemIds) ?: return
        logMockedHttpResponse(testName, mockedHttpServiceResponse)

        // then
        assertThat(mockedHttpServiceResponse.contentAsString).isEqualTo(emptyList<String>().toString())

        logTestEnded(testName)
    }

    private fun jumbleWord(word: String): String {
        val characters = word.toCharArray()
        for (i in characters.indices) {
            val randomIndex = Random.nextInt(i, characters.size)
            val temp = characters[i]
            characters[i] = characters[randomIndex]
            characters[randomIndex] = temp
        }
        return String(characters)
    }

    private fun logTestStarted(testName: String) {
        logger.info("[${ControllerConstants.TEST_CLASS_NAME}::$testName]: Test started")
    }

    private fun logTestEnded(testName: String) {
        logger.info("[${ControllerConstants.TEST_CLASS_NAME}::$testName]: Test ended")
    }

    private fun logMockedHttpResponse(testName: String, mockedHttpServletResponse: MockHttpServletResponse) {
        logger.info("[${ControllerConstants.TEST_CLASS_NAME}::$testName]: mockedHttpServletResponse: $mockedHttpServletResponse")
    }

    private fun logRepositoryIsNull(testName: String) {
        logger.error("[${ControllerConstants.TEST_CLASS_NAME}::$testName]: itemRepository is null")
    }

    private fun mockHttpGETServletResponse(): MockHttpServletResponse? = mvc?.perform(
        get(ControllerConstants.CONTROLLER_ROUTE)
            .accept(MediaType.APPLICATION_JSON)
    )
        ?.andReturn()
        ?.response

    private fun mockHttpPOSTServletResponse(lines: List<Line>): MockHttpServletResponse? = mvc?.perform(
        post(ControllerConstants.CONTROLLER_ROUTE)
            .requestAttr("lines", lines)
            .accept(MediaType.APPLICATION_JSON)
    )
        ?.andReturn()
        ?.response


    private fun mockHttpDELETEResponse(itemIds: List<String>): MockHttpServletResponse? = mvc?.perform(
        delete(ControllerConstants.CONTROLLER_ROUTE)
            .requestAttr("itemIds", itemIds)
            .accept(MediaType.APPLICATION_JSON)
    )
        ?.andReturn()
        ?.response
}