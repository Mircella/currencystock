package net.vizja.currencystock.api

import net.vizja.currencystock.CurrencyStockService
import net.vizja.currencystock.api.responses.ExchangeRateResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/exchangeRates")
class ExchangeRateController(private val currencyStockService: CurrencyStockService) {

    @GetMapping("/{currencyCode}")
    fun getExchangeRate(@PathVariable currencyCode: String): ExchangeRateResponse? {
        return currencyStockService.getCurrencyExchangeRate(currencyCode).let { ExchangeRateResponse.from(it) }
    }
}