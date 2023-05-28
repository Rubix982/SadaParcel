package com.sadapay.sadaparcel.modules.transformations

import com.sadapay.sadaparcel.modules.models.entities.EntityWithLogs
import lombok.extern.log4j.Log4j2
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.Serializable
import kotlin.reflect.KFunction1

@Log4j2
class TransformationMonadComposer {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(
            TransformationMonadComposer::class.java
        ) as Logger

        fun runWithLogs(
            entity: EntityWithLogs<Serializable?>,
            transform: KFunction1<EntityWithLogs<Serializable?>, EntityWithLogs<Serializable?>>
        ): EntityWithLogs<Serializable?> {
            val transformedEntityWithLogs = transform(entity)
            entity.logs.addAll(transformedEntityWithLogs.logs)

            return EntityWithLogs(
                entity = transformedEntityWithLogs.entity,
                logs = entity.logs,
                configProcessor = entity.configProcessor
            )
        }

        fun wrapWithLogs(
            serializableEntity: Serializable,
            configProcessor: Serializable? = null
        ): EntityWithLogs<Serializable?> {
            return EntityWithLogs(serializableEntity, mutableListOf(), configProcessor)
        }

        fun writeToLogger(logs: MutableList<String>) {
            logs.forEach { logger.info(it) }
        }
    }
}