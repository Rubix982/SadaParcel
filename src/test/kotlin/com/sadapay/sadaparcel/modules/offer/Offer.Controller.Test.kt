/*
 * Copyright 2023, Saif Ul Islam @ SadaParcel, Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the “Software”), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.sadapay.sadaparcel.modules.offer

import com.fasterxml.jackson.databind.ObjectMapper
import com.sadapay.sadaparcel.modules.models.entities.Offer
import com.sadapay.sadaparcel.modules.models.repositories.interfaces.OfferRepository
import com.sadapay.sadaparcel.modules.offer.controller.OffersController
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
internal class OfferControllerTest {

    private val logger: Logger = LoggerFactory.getLogger(
        OfferControllerTest::class.java
    ) as Logger

    private var mvc: MockMvc? = null

    @InjectMocks
    private val offerController: OffersController? = null

    @Mock
    private val offerRepository: OfferRepository? = null

    object ControllerConstants {
        const val CONTROLLER_ROUTE = "/offers"
        const val TEST_CLASS_NAME = "OfferControllerTest"
    }

    private val offers: List<Offer> = List(3) {
        Offer(
            offerId = "1",
            name = "Offer 1",
            description = "Offer 1 description",
            itemId = "item1",
            priceReduction = 0.0,
            quantityThreshold = 0
        )
        Offer(
            offerId = "2",
            name = "Offer 2",
            description = "Offer 2 description",
            itemId = "item2",
            priceReduction = 0.0,
            quantityThreshold = 0
        )
        Offer(
            offerId = "3",
            name = "Offer 3",
            description = "Offer 3 description",
            itemId = "item2",
            priceReduction = 0.0,
            quantityThreshold = 0
        )
    }

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        logger.info("[${ControllerConstants.TEST_CLASS_NAME}::setUp]: Setup complete")

        JacksonTester.initFields(this, ObjectMapper())
        mvc = MockMvcBuilders.standaloneSetup(offerController)
            .setControllerAdvice(OfferExceptionHandler())
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
    fun getWhenRepositoryIsEmptyThenReturnNoOffers() {
        val testName = "getWhenRepositoryIsEmptyThenReturnNoOffers"
        logTestStarted(testName)

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.deleteAll()
        given(offerRepository.findAll()).willReturn(emptyList())

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

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.deleteAll()
        given(offerRepository.findAll()).willReturn(emptyList())

        // when
        val mockedHttpServletResponse = mockHttpGETServletResponse() ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.status).isEqualTo(HttpStatus.OK.value())

        logTestEnded(testName)
    }

    @Test
    fun getWhenRepositoryHasOneElementThenReturnOneOffer() {
        val testName = "getWhenRepositoryHasOneElementThenReturnOneOffer"
        logTestStarted(testName)

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.deleteAll()
        offerRepository.save(offers[0])
        given(offerRepository.findAll()).willReturn(listOf(offers[0]))

        // when
        val mockedHttpServletResponse = mockHttpGETServletResponse() ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.contentLength).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun getWhenRepositoryHasOneElementThenReturnHttpStatusOK() {
        val testName = "getWhenRepositoryHasOneElementThenReturnHttpStatusOK"
        logTestStarted(testName)

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.deleteAll()
        offerRepository.save(offers[0])
        given(offerRepository.findAll()).willReturn(listOf(offers[0]))

        // when
        val mockedHttpServletResponse = mockHttpGETServletResponse() ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.status).isEqualTo(HttpStatus.OK.value())

        logTestEnded(testName)
    }

    @Test
    fun getWhenRepositoryHasOneOfferAddedTwiceAddedThenReturnOnlyOneOffer() {
        val testName = "getWhenRepositoryHasOneOfferAddedTwiceAddedThenReturnOnlyOneOffer"
        logTestStarted(testName)

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.deleteAll()
        offerRepository.save(offers[0])
        offerRepository.save(offers[0])
        given(offerRepository.findAll()).willReturn(listOf(offers[0]))

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
        logger.error("[${ControllerConstants.TEST_CLASS_NAME}::$testName]: offerRepository is null")
    }

    private fun mockHttpGETServletResponse(): MockHttpServletResponse? = mvc?.perform(
        MockMvcRequestBuilders.get(ControllerConstants.CONTROLLER_ROUTE)
            .accept(MediaType.APPLICATION_JSON)
    )
        ?.andReturn()
        ?.response
}