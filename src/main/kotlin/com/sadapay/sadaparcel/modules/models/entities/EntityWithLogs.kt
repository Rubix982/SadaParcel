package com.sadapay.sadaparcel.modules.models.entities

import java.io.Serializable

data class EntityWithLogs<T>(
    val entity: Serializable,
    val logs: MutableList<String>,
    val configProcessor: Serializable?
) : Serializable {
    fun addToLogs(log: String) {
        logs.add(log)
    }

    fun addToLogs(logs: MutableList<String>) {
        this.logs.addAll(logs)
    }
}
