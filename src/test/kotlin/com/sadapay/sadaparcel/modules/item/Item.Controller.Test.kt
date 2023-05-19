package com.sadapay.sadaparcel.modules.item

import com.fasterxml.jackson.databind.ObjectMapper
import com.sadapay.sadaparcel.modules.models.entities.Item
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
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.json.JacksonTester
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder

@Log4j2
@RunWith(MockitoJUnitRunner::class)
@ExtendWith(MockitoExtension::class)
class ItemControllerTest {

    private val logger: Logger = LoggerFactory.getLogger(
        ItemControllerTest::class.java
    ) as Logger

    private var mvc: MockMvc? = null

    @InjectMocks
    private lateinit var itemsController: ItemsController

    @Mock
    private val itemRepository: ItemRepository? = null

    object ControllerConstants {
        const val CONTROLLER_ROUTE = "/items"
        const val TEST_CLASS_NAME = "ItemControllerTest"
    }

    private val items: List<Item> = List(5) {
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
        MockitoAnnotations.openMocks(this)
        logger.info("[${ControllerConstants.TEST_CLASS_NAME}::setUp]: Setup complete")

        JacksonTester.initFields(this, ObjectMapper())
        mvc = MockMvcBuilders.standaloneSetup(itemsController)
            .setControllerAdvice(ItemExceptionHandler())
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
    fun getWhenRepositoryIsEmptyThenReturnNoItems() {
        val testName = "getWhenRepositoryIsEmptyThenReturnNoItems"
        logTestStarted(testName)

        if (itemRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        itemRepository.deleteAll()

        // when
        val mockedHttpServletResponse = mockHttpGETServletResponse() ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.contentLength).isEqualTo(0)

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
        given(itemRepository.findAll()).willReturn(listOf(items[0]))

        // when
        val mockedHttpServletResponse = mockHttpGETServletResponse() ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.contentLength).isEqualTo(itemRepository.findAll().count())

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
        given(itemRepository.findAll()).willReturn(listOf(items[0]))

        // when
        val mockedHttpServletResponse = mockHttpGETServletResponse() ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.contentLength).isEqualTo(1)

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
        MockMvcRequestBuilders.get(ControllerConstants.CONTROLLER_ROUTE)
            .accept(MediaType.APPLICATION_JSON)
    )
        ?.andReturn()
        ?.response
}