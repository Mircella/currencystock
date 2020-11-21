package net.vizja.currencystock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CurrencyStockApplication

fun main(args: Array<String>) {
    runApplication<CurrencyStockApplication>(*args)
}
