package com.sadapay.sadaparcel.bootloader

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SadaparcelApplication

fun main(args: Array<String>) {
    runApplication<SadaparcelApplication>(*args)
}
