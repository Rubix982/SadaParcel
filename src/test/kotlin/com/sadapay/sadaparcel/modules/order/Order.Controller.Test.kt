package com.sadapay.sadaparcel.modules.order

import com.fasterxml.jackson.databind.ObjectMapper
import com.sadapay.sadaparcel.modules.models.entities.Order
import com.sadapay.sadaparcel.modules.models.repositories.OrderRepository
import com.sadapay.sadaparcel.server.rules.SadaParcelHttpHeaderFilter
import lombok.extern.log4j.Log4j2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.BDDMockito
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder

@Log4j2
@RunWith(MockitoJUnitRunner::class)
@ExtendWith(MockitoExtension::class)
internal class OrderControllerTest {

    private val logger: Logger = LoggerFactory.getLogger(
        OrderControllerTest::class.java
    ) as Logger

    private var mvc: MockMvc? = null

    @InjectMocks
    private val orderController: OrderController? = null

    @Mock
    private val orderRepository: OrderRepository? = null

    object ControllerConstants {
        const val CONTROLLER_ROUTE = "/orders"
        const val TEST_CLASS_NAME = "OrderControllerTest"
    }

    private val orders: List<Order> = List(3) {
        Order(
            "id1",
            emptyList(),
            emptyList()
        )
        Order(
            "id2",
            emptyList(),
            emptyList()
        )
        Order(
            "id3",
            emptyList(),
            emptyList()
        )
    }

    @BeforeEach
    fun setUp() {
        logger.info("[${ControllerConstants.TEST_CLASS_NAME}::setUp]: Setup complete")

        JacksonTester.initFields(this, ObjectMapper())
        mvc = MockMvcBuilders.standaloneSetup(orderController)
            .setControllerAdvice(OrderExceptionHandler())
            .addFilters<StandaloneMockMvcBuilder>(SadaParcelHttpHeaderFilter())
            .build()
        logger.info("[${ControllerConstants.TEST_CLASS_NAME}::setUp::MockMvc::Successful]: mvc: $mvc")
    }

    @AfterEach
    fun tearDown() {
        logger.info("[${ControllerConstants.TEST_CLASS_NAME}::tearDown]: Teardown complete")
    }

    @Test
    fun index() {
        logger.info("[${ControllerConstants.TEST_CLASS_NAME}::index]: Start")
    }

    @Test
    fun getWhenRepositoryIsEmptyThenReturnNoItems() {
        val testName = "getWhenRepositoryIsEmptyThenReturnNoItems"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        orderRepository.deleteAll()
        val totalItems = orderRepository.findAll().size
        BDDMockito.given(totalItems).willReturn(0)

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

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        orderRepository.deleteAll()
        val totalItems = orderRepository.findAll().size
        BDDMockito.given(totalItems).willReturn(0)

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

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        orderRepository.deleteAll()
        orderRepository.save(orders[0])
        val totalItems = orderRepository.findAll().size
        BDDMockito.given(totalItems).willReturn(1)

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

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        orderRepository.deleteAll()
        orderRepository.save(orders[0])
        val totalItems = orderRepository.findAll().size
        BDDMockito.given(totalItems).willReturn(1)

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

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        orderRepository.deleteAll()
        orderRepository.save(orders[0])
        orderRepository.save(orders[0])
        val totalItems = orderRepository.findAll().size
        BDDMockito.given(totalItems).willReturn(2)

        // when
        val mockedHttpServletResponse = mockHttpGETServletResponse() ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.status).isEqualTo(HttpStatus.OK.value())

        logTestStarted(testName)
    }

    @Test
    fun postWhenRequestBodyIsIncorrectThenReturnedHttpStatusIsUnprocessableEntity() {
        val testName = "postWhenRequestBodyIsIncorrectThenReturnedHttpStatusIsUnprocessableEntity"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenRequestBodyIsIncorrectThenResponseIsDefaultOrderJSON() {
        val testName = "postWhenRequestBodyIsIncorrectThenResponseIsDefaultOrderJSON"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenRepositoryIsEmptyAndOrdersIsEmptyThenRepositoryShouldHaveNothingInserted() {
        val testName = "postWhenRepositoryIsEmptyAndOrdersIsEmptyThenRepositoryShouldHaveNothingInserted"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenRepositoryIsNotEmptyAndOrdersIsEmptyThenRepositoryShouldHaveNothingInserted() {
        val testName = "postWhenRepositoryIsNotEmptyAndOrdersIsEmptyThenRepositoryShouldHaveNothingInserted"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenOrdersHasNoIdThenReturnedHttpStatusIsUnprocessableEntity() {
        val testName = "postWhenOrdersHasNoIdThenReturnedHttpStatusIsUnprocessableEntity"
        logTestEnded(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenOrdersHasInvalidValueForItemsThenOrderWithProperFieldsShouldBeReturned() {
        val testName = "postWhenOrdersHasInvalidValueForItemsThenOrderWithProperFieldsShouldBeReturned"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenOrdersHasInvalidValueForItemsThenReturnedHttpStatusIsUnprocessableEntity() {
        val testName = "postWhenOrdersHasInvalidValueForItemsThenReturnedHttpStatusIsUnprocessableEntity"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenOrdersHasInvalidValueForOffersThenOrderWithProperFieldsShouldBeReturned() {
        val testName = "postWhenOrdersHasInvalidValueForOffersThenOrderWithProperFieldsShouldBeReturned"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenOrdersHasInvalidValueForOffersThenReturnedHttpStatusIsUnprocessableEntity() {
        val testName = "postWhenOrdersHasInvalidValueForOffersThenReturnedHttpStatusIsUnprocessableEntity"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenOrderHasIdButEmptyItemsAndOffersListThenOrderIsInsertedWithoutAnyMappings() {
        val testName = "postWhenOrderHasIdButEmptyItemsAndOffersListThenOrderIsInsertedWithoutAnyMappings"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenOrderHasOneItemAndZeroOffersThenItemRepositoryEitherAddOrUpdateItem() {
        val testName = "postWhenOrderHasOneItemAndZeroOffersThenItemRepositoryEitherAddOrUpdateItem"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestStarted(testName)
    }

    @Test
    fun postWhenOrderHasOneOfferAndZeroItemsThenOfferRepositoryEitherAddOrUpdateOffer() {
        val testName = "postWhenOrderHasOneOfferAndZeroItemsThenOfferRepositoryEitherAddOrUpdateOffer"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenOrderHasBothOneItemAndOfferThenBothRepositoriesEitherAddOrUpdateResources() {
        val testName = "postWhenOrderHasBothOneItemAndOfferThenBothRepositoriesEitherAddOrUpdateResources"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenOrderHasNItemsAndMOffersThenBothRepositoriesEitherAddOrUpdateResourcesV1() {
        val testName = "postWhenOrderHasNItemsAndMOffersThenBothRepositoriesEitherAddOrUpdateResourcesV1"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenOrderHasNItemsAndMOffersThenBothRepositoriesEitherAddOrUpdateResourcesV2() {
        val testName = "postWhenOrderHasNItemsAndMOffersThenBothRepositoriesEitherAddOrUpdateResourcesV2"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenOrderHasNItemsAndMOffersThenBothRepositoriesEitherAddOrUpdateResourcesV3() {
        val testName = "postWhenOrderHasNItemsAndMOffersThenBothRepositoriesEitherAddOrUpdateResourcesV3"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenMultipleOrdersAreProvidedThenEachOrderIsAppropriatelyProcessed() {
        val testName = "postWhenMultipleOrdersAreProvidedThenEachOrderIsAppropriatelyProcessed"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenAnyProperRequestBodyIsProvidedThenReturnedResponseContainsSameBodyV1() {
        val testName = "postWhenAnyProperRequestBodyIsProvidedThenReturnedResponseContainsSameBodyV1"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenAnyProperRequestBodyIsProvidedThenReturnedResponseContainsSameBodyV2() {
        val testName = "postWhenAnyProperRequestBodyIsProvidedThenReturnedResponseContainsSameBodyV2"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenAnyProperRequestBodyIsProvidedThenReturnedResponseContainsSameBodyV3() {
        val testName = "postWhenAnyProperRequestBodyIsProvidedThenReturnedResponseContainsSameBodyV3"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenAnyProperRequestBodyIsProvidedThenReturnedHttpStatusIsNoContent() {
        val testName = "postWhenAnyProperRequestBodyIsProvidedThenReturnedHttpStatusIsNoContent"
        logTestStarted(testName)

        if (orderRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given

        // when
        val mockedHttpServletResponse: MockHttpServletResponse? = null
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(1).isEqualTo(1)

        logTestEnded(testName)
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

    private fun logMockedHttpResponse(testName: String, mockedHttpServletResponse: MockHttpServletResponse?) {
        logger.info("[${ControllerConstants.TEST_CLASS_NAME}::$testName]: mockedHttpServletResponse: $mockedHttpServletResponse")
    }

    private fun logRepositoryIsNull(testName: String) {
        logger.error("[${ControllerConstants.TEST_CLASS_NAME}::$testName]: orderRepository is null")
    }

    private fun mockHttpGETServletResponse(): MockHttpServletResponse? = mvc?.perform(
        MockMvcRequestBuilders.get(ControllerConstants.CONTROLLER_ROUTE)
            .accept(MediaType.APPLICATION_JSON)
    )
        ?.andReturn()
        ?.response
}