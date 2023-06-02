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

package com.sadapay.sadaparcel.modules.offersmanagement

import com.fasterxml.jackson.databind.ObjectMapper
import com.sadapay.sadaparcel.modules.itemsmanagement.ItemsManagementControllerTest
import com.sadapay.sadaparcel.modules.models.entities.Offer
import com.sadapay.sadaparcel.modules.models.repositories.interfaces.OfferRepository
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder
import kotlin.random.Random

@Log4j2
@RunWith(MockitoJUnitRunner::class)
@ExtendWith(MockitoExtension::class)
internal class OffersManagementControllerTest {

    private val logger: Logger = LoggerFactory.getLogger(
        OffersManagementControllerTest::class.java
    ) as Logger

    private var mvc: MockMvc? = null

    @InjectMocks
    private val offersManagementController: OffersManagementController? = null

    @Mock
    private val offerRepository: OfferRepository? = null

    object ControllerConstants {
        const val CONTROLLER_ROUTE = "/offers-management"
        const val TEST_CLASS_NAME = "OffersManagementControllerTest"
    }

    private val offers: List<Offer> = List(3) {
        Offer(
            "id1",
            "name",
            "description",
            "itemId1",
            0.0,
            1
        )
        Offer(
            "id2",
            "name",
            "description",
            "itemId2",
            0.0,
            1
        )
        Offer(
            "id3",
            "name",
            "description",
            "itemId3",
            0.0,
            1
        )
    }

    @BeforeEach
    fun setUp() {
        logger.info("[${ControllerConstants.TEST_CLASS_NAME}::setUp]: Setup complete")

        JacksonTester.initFields(this, ObjectMapper())
        mvc = MockMvcBuilders.standaloneSetup(offersManagementController)
            .setControllerAdvice(OffersManagementExceptionHandler())
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
        logger.info("[${ControllerConstants.TEST_CLASS_NAME}::index]: ${ItemsManagementControllerTest.ControllerConstants.TEST_CLASS_NAME} detected")
    }

    @Test
    fun postWhenNoOfferIsAddedThenHttpStatusShouldBeNoContent() {
        val testName = "postWhenNoOfferIsAddedThenHttpStatusShouldBeNoContent"
        logTestStarted(testName)

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        given(offerRepository.findAll()).willReturn(mutableListOf())

        // when
        val mockedHttpServletResponse = mockHttpPOSTServletResponse(mutableListOf()) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.status).isEqualTo(204)

        logTestEnded(testName)
    }

    @Test
    fun postWhenNoOfferIsAddedThenNoItemIsReturned() {
        val testName = "postWhenNoItemIsAddedThenNoItemIsReturned"
        logTestStarted(testName)

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // when
        val mockedHttpServletResponse = mockHttpPOSTServletResponse(mutableListOf()) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.contentLength).isEqualTo(0)

        logTestEnded(testName)
    }

    @Test
    fun postWhenOneOfferIsAddedAndRepositoryIsEmptyThenHttpStatusShouldBeCreated() {
        val testName = "postWhenOneOfferIsAddedAndRepositoryIsEmptyThenHttpStatusShouldBeCreated"
        logTestStarted(testName)

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.deleteAll()
        given(offerRepository.findAll()).willReturn(mutableListOf())

        // when
        val offer = List(1) { offers[0] }
        val mockedHttpServletResponse = mockHttpPOSTServletResponse(offer) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.status).isEqualTo(201)

        logTestEnded(testName)
    }

    @Test
    fun postWhenOneItemIsAddedAndRepositoryIsEmptyThenRepositoryShouldPersistOneItem() {
        val testName = "postWhenOneItemIsAddedAndRepositoryIsEmptyThenRepositoryShouldPersistNewItem"
        logTestStarted(testName)

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.deleteAll()
        given(offerRepository.findAll()).willReturn(mutableListOf())

        // when
        val offer = List(1) { offers[0] }
        val mockedHttpServletResponse = mockHttpPOSTServletResponse(offer) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(offerRepository.findAll().count()).isEqualTo(1)

        logTestEnded(testName)
    }

    @Test
    fun postWhenOneItemIsAddedAndRepositoryIsEmptyThenRepositoryShouldPersistDataAsExpected() {
        val testName = "postWhenOneItemIsAddedAndRepositoryIsEmptyThenRepositoryShouldPersistDataAsExpected"
        logTestStarted(testName)

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.deleteAll()
        given(offerRepository.findAll()).willReturn(mutableListOf())

        // when
        val offer = List(1) { offers[0] }
        val mockedHttpServletResponse = mockHttpPOSTServletResponse(offer) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(offerRepository.findAll()).isEqualTo(mutableListOf(offers[0]))

        logTestEnded(testName)
    }

    @Test
    fun postWhenRepositoryIsNotEmptyAndItemIsNotPreviouslyAddedThenHttpStatusShouldBeCreated() {
        val testName = "postWhenOneItemIsAddedThenHttpStatusShouldBeCreated"
        logTestStarted(testName)

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.deleteAll()
        offerRepository.save(offers[0])
        given(offerRepository.findAll()).willReturn(mutableListOf(offers[0]))

        // when
        val offer = List(1) { offers[1] }
        val mockedHttpServletResponse = mockHttpPOSTServletResponse(offer) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.status).isEqualTo(HttpStatus.CREATED.value())

        logTestEnded(testName)
    }

    @Test
    fun postWhenRepositoryIsNotEmptyAndItemExistsPreviouslyThenHttpStatusBeShouldOK() {
        val testName = "postWhenOneItemIsAddedThenHttpStatusShouldBeCreated"
        logTestStarted(testName)

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.deleteAll()
        offerRepository.save(offers[0])
        given(offerRepository.findAll()).willReturn(mutableListOf(offers[0]))

        // when
        val offer = List(1) { offers[0] }
        val mockedHttpServletResponse = mockHttpPOSTServletResponse(offer) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.status).isEqualTo(HttpStatus.OK.value())

        logTestEnded(testName)
    }

    @Test
    fun postWhenRepositoryIsNotEmptyAndItemExistsPreviouslyThenRepositoryIsUpdated() {
        val testName = "postWhenOneItemIsAddedThenHttpStatusShouldBeCreated"
        logTestStarted(testName)

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.deleteAll()
        offerRepository.save(offers[0])
        given(offerRepository.findAll().count()).willReturn(1)

        // when
        val firstOffer = List(1) { offers[0] }
        firstOffer[0].name = "new name"
        val mockedHttpServletResponse = mockHttpPOSTServletResponse(firstOffer) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        val offersFromRepository = offerRepository.findAll().toList()
        val offer: Offer? = offersFromRepository.getOrNull(0)
        assertThat(offer != null).isEqualTo(true)
        assertThat(offer?.name).isEqualTo("new name")

        logTestEnded(testName)
    }

    @Test
    fun postWhenAnyLinesArePassedThenThoseLinesAreReturnedExactly() {
        val testName = "postWhenAnyLinesArePassedThenThoseLinesAreReturnedExactly"
        logTestStarted(testName)

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // when
        val offers = List(3) {
            offers[0]
            offers[1]
            offers[2]
        }
        val mockedHttpServletResponse = mockHttpPOSTServletResponse(offers) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.contentLength).isEqualTo(offers.size)

        logTestEnded(testName)
    }

    @Test
    fun getWhenRepositoryIsEmptyThenReturnNoItems() {
        val testName = "getWhenRepositoryIsEmptyThenReturnNoItems"
        logTestStarted(testName)

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.deleteAll()
        given(offerRepository.findAll()).willReturn(mutableListOf())

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
        given(offerRepository.findAll()).willReturn(mutableListOf())

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

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.deleteAll()
        offerRepository.save(offers[0])
        given(offerRepository.findAll()).willReturn(mutableListOf(offers[0]))

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
        given(offerRepository.findAll()).willReturn(mutableListOf(offers[0]))

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

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.deleteAll()
        offerRepository.save(offers[0])
        offerRepository.save(offers[0])
        given(offerRepository.findAll()).willReturn(mutableListOf(offers[0]))

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

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        val itemCount = offerRepository.findAll().count()

        // when
        val mockedHttpServletResponse = mockHttpDELETEResponse(mutableListOf()) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(offerRepository.findAll().count()).isEqualTo(itemCount)

        logTestEnded(testName)
    }

    @Test
    fun deleteWhenNoItemIdsArePassedThenHttpStatusIsNoContent() {
        val testName = "deleteWhenNoItemIdsArePassedThenHttpStatusIsNoContent"
        logTestStarted(testName)

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // when
        val mockedHttpServletResponse = mockHttpDELETEResponse(mutableListOf()) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(mockedHttpServletResponse.status).isEqualTo(HttpStatus.NO_CONTENT.value())

        logTestEnded(testName)
    }

    @Test
    fun deleteWhenAllItemIdsAreValidThenRepositoryIsAffected() {
        val testName = "deleteWhenAllItemIdsAreValidThenRepositoryIsAffected"
        logTestStarted(testName)

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.saveAll(offers)

        // when
        val itemIds: MutableList<String> = mutableListOf()
        offers.forEach { offer -> itemIds.add(offer.itemId) }
        val mockedHttpServletResponse = mockHttpDELETEResponse(itemIds) ?: return
        logMockedHttpResponse(testName, mockedHttpServletResponse)

        // then
        assertThat(offerRepository.findAll().count()).isEqualTo(0)

        logTestEnded(testName)
    }

    @Test
    fun deleteWhenAllItemIdsAreValidThenHttpStatusIsNoContent() {
        val testName = "deleteWhenAllItemIdsAreValidThenHttpStatusIsNoContent"
        logTestStarted(testName)

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.saveAll(offers)

        // when
        val itemIds: MutableList<String> = mutableListOf()
        offers.forEach { offer -> itemIds.add(offer.offerId) }
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

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.saveAll(offers)

        // when
        val itemIds: MutableList<String> = mutableListOf()
        offers.forEach { offer -> itemIds.add(offer.offerId) }
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

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.saveAll(offers)

        // when
        val itemIds: MutableList<String> = mutableListOf()
        offers.forEachIndexed { index, offer ->
            run {
                var itemId: String = offer.offerId
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

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.saveAll(offers)

        // when
        val itemIds: MutableList<String> = mutableListOf()
        offers.forEachIndexed { index, offer ->
            run {
                var itemId: String = offer.offerId
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

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.saveAll(offers)

        // when
        val itemIds: MutableList<String> = mutableListOf()
        offers.forEach { offer -> itemIds.add(jumbleWord(offer.offerId)) }
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

        if (offerRepository == null) {
            logRepositoryIsNull(testName)
            return
        }

        // given
        offerRepository.saveAll(offers)

        // when
        val itemIds: MutableList<String> = mutableListOf()
        offers.forEach { offer -> itemIds.add(jumbleWord(offer.offerId)) }
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
        logger.error("[${ControllerConstants.TEST_CLASS_NAME}::$testName]: offerRepository is null")
    }

    private fun mockHttpGETServletResponse(): MockHttpServletResponse? = mvc?.perform(
        MockMvcRequestBuilders.get(ControllerConstants.CONTROLLER_ROUTE)
            .accept(MediaType.APPLICATION_JSON)
    )
        ?.andReturn()
        ?.response

    private fun mockHttpPOSTServletResponse(offers: List<Offer>): MockHttpServletResponse? = mvc?.perform(
        MockMvcRequestBuilders.post(ControllerConstants.CONTROLLER_ROUTE)
            .requestAttr("offers", offers)
            .accept(MediaType.APPLICATION_JSON)
    )
        ?.andReturn()
        ?.response


    private fun mockHttpDELETEResponse(offerIds: List<String>): MockHttpServletResponse? = mvc?.perform(
        MockMvcRequestBuilders.delete(ControllerConstants.CONTROLLER_ROUTE)
            .requestAttr("offerIds", offerIds)
            .accept(MediaType.APPLICATION_JSON)
    )
        ?.andReturn()
        ?.response
}