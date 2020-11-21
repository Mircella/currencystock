package net.vizja.currencystock

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CurrencyStockController {
    @GetMapping
    fun getCurrency(): String {
        return "Zl"
    }
}