package com.sadapay.sadaparcel.modules.itemsmanagement

import com.fasterxml.jackson.databind.ObjectMapper
import com.sadapay.sadaparcel.modules.models.entities.Item
import com.sadapay.sadaparcel.modules.models.entities.Line
import com.sadapay.sadaparcel.modules.models.repositories.ItemRepository
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
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder


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

    @BeforeEach
    fun setUp() {
        logger.info("[ItemsManagementControllerTest::setUp::]: Setup complete")

        JacksonTester.initFields(this, ObjectMapper())
        mvc = MockMvcBuilders.standaloneSetup(itemsManagementController)
            .setControllerAdvice(ItemsManagementExceptionHandler())
            .addFilters<StandaloneMockMvcBuilder>(ItemFilter())
            .build()
        logger.info("[ItemsManagementControllerTest::setUp::MockMvc::Successful]: mvc: $mvc")
    }

    @AfterEach
    fun tearDown() {
        logger.info("[ItemsManagementControllerTest::closeUp]: Tear down complete")
    }

    @Test
    fun index() {
        logger.info("[ItemsManagementControllerTest::index]: Test started")
        logger.info("[ItemsManagementControllerTest::index]: Test ended")
    }

    @Test
    fun addNoItemShouldReturnHttpStatusNoContent() {
        logger.info(
            "[ItemsManagementControllerTest::addNoItemShouldReturnHttpStatusNoContent]: " +
                    "Test started"
        )

        if (itemRepository == null) {
            logger.error(
                "[ItemsManagementControllerTest::addNoItemShouldReturnHttpStatusNoContent]: " +
                        "itemRepository is null"
            )
            return
        }

        // given
        given(itemRepository.findAll()).willReturn(emptyList())

        // when
        val mockedHttpServletResponse =
            mvc?.perform(
                post("/items-management")
                    .requestAttr("lines", emptyList<Line>())
                    .accept(MediaType.APPLICATION_JSON)
            )
                ?.andReturn()
                ?.response
                ?: return
        logger.info(
            "[ItemsManagementControllerTest::addNoItemShouldReturnHttpStatusNoContent]: " +
                    "mockedHttpServletResponse: $mockedHttpServletResponse"
        )

        // then
        assertThat(mockedHttpServletResponse.status).isEqualTo(204)

        logger.info("[addNoItemShouldReturnNoItems::addNoItemShouldReturnHttpStatusNoContent]: Test ended")
    }

    @Test
    fun addNoItemShouldReturnNoItems() {
        logger.info("[ItemsManagementControllerTest::addNoItemShouldReturnNoItems]: Test started")

        if (itemRepository == null) {
            logger.error("[ItemsManagementControllerTest::addNoItemShouldReturnNoItems]: itemRepository is null")
            return
        }

        // given
        itemRepository.save(Item())
        val totalItems = itemRepository.findAll().size
        given(totalItems).willReturn(1)

        // when
        val mockedHttpServletResponse =
            mvc?.perform(get("/items-management").accept(MediaType.APPLICATION_JSON))?.andReturn()?.response ?: return
        logger.info("[ItemsManagementControllerTest::addNoItemShouldReturnNoItems]: mockedHttpServletResponse: $mockedHttpServletResponse")

        // then
        assertThat(mockedHttpServletResponse.contentLength).isEqualTo(totalItems)

        logger.info("[ItemsManagementControllerTest::addNoItemShouldReturnNoItems]: Test ended")
    }
}